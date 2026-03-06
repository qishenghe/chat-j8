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

        ChatClient client = ChatClient.builder()
                .baseUrl(baseUrl)
                .model(model)
                .build();

        // call
        String content = client.prompt("You are a helpful assistant.")
                .user("你好")
                .call()
                .content();

        System.out.println(content);

        // stream

        ChatListener<ChatResult> listener = new ChatListener<ChatResult>() {
            @Override
            public void msg(EventSource eventSource, String id, String type, ChatResult data) {
                System.out.println(data.getChoices().get(0).getDelta().getContent());
            }
        };

        client.prompt()
                .user("hello")
                .stream(listener, true);

        System.out.println();

    }

}
