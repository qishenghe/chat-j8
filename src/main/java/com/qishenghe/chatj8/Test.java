package com.qishenghe.chatj8;

import com.qishenghe.chatj8.api.entity.function.ToolCall;
import com.qishenghe.chatj8.api.sse.listener.ChatListener;
import com.qishenghe.chatj8.api.sse.listener.ToolCallsListener;
import com.qishenghe.chatj8.client.ChatClient;
import com.qishenghe.chatj8.client.entity.ChatResult;
import com.qishenghe.chatj8.client.entity.spec.ChatResponseSpec;
import com.qishenghe.chatj8.client.tool.ToolBuilder;
import com.qishenghe.chatj8.client.tool.ToolInvoker;
import com.qishenghe.chatj8.demo.TestTools;
import okhttp3.sse.EventSource;

import java.util.ArrayList;
import java.util.List;

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

        // function call
        ChatResponseSpec responseSpec = client.prompt("You are a helpful assistant.")
                .user("今天北京天气如何")
                .tools(ToolBuilder.buildTools(TestTools.class))
                .call();

        List<ToolCall> toolCalls = responseSpec.getResult().getChoices().get(0).getMessage().getToolCalls();

        for (ToolCall single : toolCalls) {
            Object o = ToolInvoker.invokeTool(TestTools.class, single);
            System.out.println(o);
        }

        System.out.println("===========================");

        ChatListener<ChatResult> listener2 = new ChatListener<ChatResult>() {
            @Override
            public void msg(EventSource eventSource, String id, String type, ChatResult data) {
                System.out.println(data.getChoices().get(0).getDelta().getContent());
            }
        };

        List<ToolCall> toolCalls1 = new ArrayList<>();

        client.prompt("You are a helpful assistant.")
                .user("今天北京天气如何")
                .tools(ToolBuilder.buildTools(TestTools.class))
                .toolCallsListener(new ToolCallsListener() {
                    @Override
                    public void toolCall(ToolCall toolCall) {
                        toolCalls1.add(toolCall);
                    }
                })
                .stream(listener2, true);

        System.out.println();

    }

}
