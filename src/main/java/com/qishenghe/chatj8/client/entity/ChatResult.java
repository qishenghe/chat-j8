package com.qishenghe.chatj8.client.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 15:16
 * @since 2026/3/6 15:16 by qishenghe for init
 */
@Data
public class ChatResult implements Serializable {

    private static final long serialVersionUID = 4968921860894991299L;

    /**
     * id
     */
    private String id;

    /**
     * choice
     */
    private List<ChatChoice> choices;

}
