package com.qishenghe.chatj8.api;

import cn.hutool.http.ContentType;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qishenghe.chatj8.api.request.CompletionsRequest;
import com.qishenghe.chatj8.api.response.CompletionsResponse;
import lombok.Data;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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

        String response = HttpUtil.createPost(baseUrl + apiPath)
                .disableCookie()
                .addHeaders(headers())
                .body(jsonString, ContentType.JSON.getValue())
                .execute()
                .body();

        return processResult(response, new TypeReference<CompletionsResponse>(){});
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
