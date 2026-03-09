package com.qishenghe.chatj8.client.entity.spec;

import com.qishenghe.chatj8.api.entity.function.ToolFunction;
import com.qishenghe.chatj8.api.sse.listener.ChatListener;
import com.qishenghe.chatj8.api.sse.listener.ToolCallsListener;
import com.qishenghe.chatj8.client.ChatClient;
import com.qishenghe.chatj8.client.entity.ChatMessage;
import com.qishenghe.chatj8.client.entity.ChatParam;
import com.qishenghe.chatj8.client.entity.ChatResult;
import com.qishenghe.chatj8.enun.ChatRoleEnum;
import lombok.Data;
import okhttp3.sse.EventSource;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 16:14
 * @since 2026/3/6 16:14 by qishenghe for init
 */
@Data
public class ChatRequestSpec {

    /**
     * client
     */
    private ChatClient chatClient;

    /**
     * chat param
     */
    private ChatParam chatParam;

    /**
     * tool calls listener
     */
    private ToolCallsListener toolCallsListener;

    /**
     * add
     *
     * @param role    role
     * @param content content
     * @return result
     * @author qishenghe
     * @date 2026/3/6 17:58
     */
    public ChatRequestSpec add(String role, Object content) {

        if (chatParam.getMessages() == null) {
            chatParam.setMessages(new ArrayList<>());
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole(role);
        chatMessage.setContent(content);

        chatParam.getMessages().add(chatMessage);

        return this;
    }

    /**
     * add all
     *
     * @param messages messages
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:01
     */
    public ChatRequestSpec addAll(List<ChatMessage> messages) {

        if (messages != null) {
            chatParam.getMessages().addAll(messages);
        }

        return this;
    }

    /**
     * think able
     *
     * @param think think
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:02
     */
    public ChatRequestSpec thinkAble(boolean think) {

        chatParam.setThinkAble(think);

        return this;
    }

    /**
     * system
     *
     * @param content content
     * @return result
     * @author qishenghe
     * @date 2026/3/6 17:56
     */
    public ChatRequestSpec system(String content) {

        return add(ChatRoleEnum.SYSTEM.getCode(), content);
    }

    /**
     * user
     *
     * @param content content
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:05
     */
    public ChatRequestSpec user(String content) {

        return add(ChatRoleEnum.USER.getCode(), content);
    }

    /**
     * tools
     *
     * @param tool tool
     * @return result
     * @author qishenghe
     * @date 2026/3/6 19:00
     */
    public ChatRequestSpec tool(ToolFunction tool) {

        if (chatParam.getTools() == null) {
            chatParam.setTools(new ArrayList<>());
        }

        if (tool != null) {
            chatParam.getTools().add(tool);
        }

        return this;
    }

    /**
     * tools
     *
     * @param tools tools
     * @return result
     * @author qishenghe
     * @date 2026/3/6 19:00
     */
    public ChatRequestSpec tools(List<ToolFunction> tools) {

        if (tools != null && !tools.isEmpty()) {

            for (ToolFunction single : tools) {
                ChatRequestSpec tool = tool(single);
            }
        }

        return this;
    }

    /**
     * tool calls listener
     *
     * @param toolCallsListener toolCallsListener
     * @return result
     * @author qishenghe
     * @date 2026/3/7 15:46
     */
    public ChatRequestSpec toolCallsListener(ToolCallsListener toolCallsListener) {
        this.toolCallsListener = toolCallsListener;
        return this;
    }

    /**
     * call
     *
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:07
     */
    public ChatResponseSpec call() {

        ChatResult chatResult = chatClient.completions(chatParam);

        ChatResponseSpec responseSpec = new ChatResponseSpec(chatResult);

        return responseSpec;
    }

    /**
     * stream
     * （non blocking）
     *
     * @param listener listener
     * @author qishenghe
     * @date 2026/3/6 18:12
     */
    public void stream(ChatListener<ChatResult> listener) {
        // non blocking default
        stream(listener, false);
    }

    /**
     * stream
     *
     * @param listener listener
     * @param block    block
     * @author qishenghe
     * @date 2026/3/6 18:12
     */
    public void stream(ChatListener<ChatResult> listener, boolean block) {

        if (!block) {
            chatClient.completions(chatParam, listener);
        } else {
            CountDownLatch countDownLatch = new CountDownLatch(1);

            ChatListener<ChatResult> wrappedListener = new ChatListener<ChatResult>() {
                @Override
                public void msg(EventSource eventSource, String id, String type, ChatResult data) {
                    listener.msg(eventSource, id, type, data);
                }

                @Override
                public void onClosed(@NotNull EventSource eventSource) {
                    try {
                        listener.onClosed(eventSource);
                    } finally {
                        countDownLatch.countDown();
                    }
                }

                @Override
                public void onFailure(@NotNull EventSource eventSource, Throwable t, okhttp3.Response response) {
                    try {
                        listener.onFailure(eventSource, t, response);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            };

            // 复制 listener 的 safeWord 到包装的 listener（processor 会在 chatClient.completions 中设置）
            wrappedListener.setSafeWord(listener.getSafeWord());

            chatClient.completions(chatParam, wrappedListener);

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

}
