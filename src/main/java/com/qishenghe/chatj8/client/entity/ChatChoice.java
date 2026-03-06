package com.qishenghe.chatj8.client.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 15:17
 * @since 2026/3/6 15:17 by qishenghe for init
 */
@Data
public class ChatChoice implements Serializable {

    private static final long serialVersionUID = -1255122962345329885L;

    /**
     * index
     */
    private Integer index;

    /**
     * message
     */
    private ChatMessage message;

    /**
     * delta
     */
    private ChatMessage delta;

}
