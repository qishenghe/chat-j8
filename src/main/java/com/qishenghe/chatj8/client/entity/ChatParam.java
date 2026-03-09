package com.qishenghe.chatj8.client.entity;

import com.qishenghe.chatj8.api.entity.function.ToolFunction;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 14:13
 * @since 2026/3/6 14:13 by qishenghe for init
 */
@Data
public class ChatParam implements Serializable {

    private static final long serialVersionUID = 2387890935915524265L;

    /**
     * think
     */
    private Boolean thinkAble = false;

    /**
     * messages
     */
    private List<ChatMessage> messages;

    /**
     * tools
     */
    private List<ToolFunction> tools;

}
