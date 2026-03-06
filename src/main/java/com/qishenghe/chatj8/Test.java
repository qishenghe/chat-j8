package com.qishenghe.chatj8;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qishenghe.chatj8.api.ChatApiUtil;
import com.qishenghe.chatj8.api.entity.Message;
import com.qishenghe.chatj8.api.request.CompletionsRequest;
import com.qishenghe.chatj8.api.response.CompletionsResponse;
import com.qishenghe.chatj8.api.sse.DecoratorListener;
import com.qishenghe.chatj8.enun.ChatRoleEnum;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * chat-j8
 *
 * @author qishenghe
 */
public class Test {

    public static void main(String[] args) {

        String baseUrl = "http://192.168.205.143:7000";
        String model = "Qwen/Qwen3.5-35B-A3B";

        ChatApiUtil util = new ChatApiUtil();

        util.setBaseUrl(baseUrl);

        CompletionsRequest request = new CompletionsRequest();
        request.setModel(model);

        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        message.setRole(ChatRoleEnum.USER.getCode());
        message.setContent("你好");
        messages.add(message);

        request.setMessages(messages);

//        CompletionsResponse completions = util.completions(request);

        CountDownLatch latch = new CountDownLatch(1);

        util.completions(request, new DecoratorListener<CompletionsResponse>() {
            @Override
            public void msg(EventSource eventSource, String id, String type, CompletionsResponse data) {
                System.out.println(data);
            }

            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                super.onClosed(eventSource);
                latch.countDown();
            }

            @Override
            public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                super.onFailure(eventSource, t, response);
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
    }

}
