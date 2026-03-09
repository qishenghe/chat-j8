package com.qishenghe.chatj8.api.sse.listener;

import com.qishenghe.chatj8.api.entity.function.ToolCall;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/7 15:42
 * @since 2026/3/7 15:42 by qishenghe for init
 */
public abstract class ToolCallsListener {

    /**
     * tool call
     *
     * @param toolCall toolCall
     * @author qishenghe
     * @date 2026/3/7 15:44
     */
    public abstract void toolCall (ToolCall toolCall);

}
