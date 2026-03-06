package com.qishenghe.chatj8.api;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qishenghe.chatj8.api.request.CompletionsRequest;
import com.qishenghe.chatj8.api.response.CompletionsResponse;
import com.qishenghe.chatj8.api.sse.ChatListener;
import com.qishenghe.chatj8.api.sse.pro.EventSourceProcessor;
import lombok.Data;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 18:56
 * @since 2026/3/5 18:56 by qishenghe for init
 */
@Data
public class ChatApiUtil {

    /**
     * base url
     */
    private String baseUrl;

    /**
     * api path
     */
    private String apiPath = "/v1/chat/completions";

    /**
     * api key
     */
    private String apiKey;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * completions
     *
     * @param request request
     * @return result
     * @author qishenghe
     * @date 2026/3/5 19:32
     */
    public CompletionsResponse completions (CompletionsRequest request) {

        String jsonString = JSON.toJSONString(request);

        String response;

        try (HttpResponse httpResponse = HttpUtil.createPost(baseUrl + apiPath)
                .disableCookie()
                .addHeaders(headers())
                .body(jsonString, ContentType.JSON.getValue())
                .execute()) {
            response = httpResponse.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return processResult(response, new TypeReference<CompletionsResponse>(){});
    }

    /**
     * completions
     *
     * @param request request
     * @param listener listener
     * @author qishenghe
     * @date 2026/3/6 11:44
     */
    public void completions (CompletionsRequest request, ChatListener<CompletionsResponse> listener) {

        listener.setProcessor(new EventSourceProcessor<CompletionsResponse>() {
            @Override
            public CompletionsResponse process(String data) {
                return processResult(data, new TypeReference<CompletionsResponse>() {});
            }
        });

        completionsStream(request, listener);
    }

    /**
     * completions stream
     *
     * @param request request
     * @param listener listener
     * @author qishenghe
     * @date 2026/3/6 11:30
     */
    public void completionsStream (CompletionsRequest request, EventSourceListener listener) {
        String jsonString = JSON.toJSONString(request);

        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        // 设置为流式返回
        jsonObject.put("stream", true);

        jsonString = jsonObject.toJSONString();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        // 创建JSON数据的RequestBody
        RequestBody body = RequestBody.create(
                jsonString,
                MediaType.parse("application/json; charset=utf-8")
        );

        Map<String, String> headers = headers();
        headers.put("accept", "application/json, text/event-stream");

        Request sseRequest = new Request.Builder()
                .url(baseUrl + apiPath)
                .headers(Headers.of(headers))
                .post(body)
                .build();

        EventSource.Factory factory = EventSources.createFactory(client);

        // 创建一个新的事件源，开始监听SSE流
        factory.newEventSource(sseRequest, listener);
    }

    /**
     * headers
     *
     * @return result
     * @author qishenghe
     * @date 2026/3/5 19:34
     */
    private Map<String, String> headers () {

        Map<String, String> headers = new HashMap<>(16);

        if (apiKey != null && !apiKey.isEmpty()) {
            headers.put("Authorization", "Bearer " + apiKey);
        }

        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {

            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            String authHeader = "Basic " + encodedCredentials;

            headers.put("Authorization", authHeader);
        }

        return headers;
    }

    /**
     * process result
     *
     * @param response response
     * @param type type
     * @return result
     * @author qishenghe
     * @date 2026/3/5 19:33
     */
    public <T> T processResult (String response, TypeReference<T> type) {

        T result = null;
        try {
            JSONObject jsonObject = JSONObject.parseObject(response);

            result = JSONObject.parseObject(jsonObject.toJSONString(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
