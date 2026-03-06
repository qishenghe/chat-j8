package com.qishenghe.chatj8;

import com.qishenghe.chatj8.api.sse.ChatListener;
import com.qishenghe.chatj8.client.ChatClient;
import com.qishenghe.chatj8.client.entity.ChatMessage;
import com.qishenghe.chatj8.client.entity.ChatParam;
import com.qishenghe.chatj8.client.entity.ChatResult;
import okhttp3.sse.EventSource;
import org.jetbrains.annotations.NotNull;

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

        ChatClient build = ChatClient.builder()
                .baseUrl(baseUrl)
                .model(model)
                .build();

        List<ChatMessage> chatMessages = ChatMessage.builder()
                .user("你好")
                .build();

        ChatParam chatParam = new ChatParam();
        chatParam.setMessages(chatMessages);

        ChatResult completions = build.completions(chatParam);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        build.completions(chatParam, new ChatListener<ChatResult>() {
            @Override
            public void msg(EventSource eventSource, String id, String type, ChatResult data) {
                System.out.println(data.getChoices().get(0).getDelta().getContent());
            }

            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                super.onClosed(eventSource);
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
    }

}
