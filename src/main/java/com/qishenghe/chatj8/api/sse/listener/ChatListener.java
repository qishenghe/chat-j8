package com.qishenghe.chatj8.api.sse.listener;

import com.qishenghe.chatj8.api.sse.pro.EventSourceProcessor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 11:36
 * @since 2026/3/6 11:36 by qishenghe for init
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ChatListener<T> extends EventSourceListener {

    /**
     * processor
     */
    private EventSourceProcessor<T> processor;

    /**
     * safe word（stop sign）
     */
    private String safeWord = "[DONE]";

    /**
     * msg listen
     *
     * @param eventSource eventSource
     * @param id id
     * @param type type
     * @param data data
     * @author qishenghe
     * @date 2026/3/6 11:40
     */
    public abstract void msg(EventSource eventSource, String id, String type, T data);

    /**
     * on event
     *
     * @param eventSource eventSource
     * @param id id
     * @param type type
     * @param data data
     * @author qishenghe
     * @date 2026/3/6 11:42
     */
    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        super.onEvent(eventSource, id, type, data);

        if (safeWord == null || !safeWord.equals(data)) {
            msg(eventSource, id, type, processor.process(data));
        }
    }

}
