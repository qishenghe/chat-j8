package com.qishenghe.chatj8.api.sse.pro;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 11:38
 * @since 2026/3/6 11:38 by qishenghe for init
 */
public abstract class EventSourceProcessor<T> {

    /**
     * process event source msg
     *
     * @param data data
     * @return result
     * @author qishenghe
     * @date 2026/3/6 11:38
     */
    public abstract T process (String data);

}
