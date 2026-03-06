package com.qishenghe.chatj8.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:30
 * @since 2026/3/5 19:30 by qishenghe for init
 */
@Data
public class Choice implements Serializable {

    private static final long serialVersionUID = 4732540228046515212L;

    /**
     * index
     */
    private Integer index;

    /**
     * message
     */
    private Message message;

    /**
     * message
     */
    private Message delta;

    /**
     * logprobs
     */
    private Object logprobs;

    /**
     * finish_reason
     */
    private String finish_reason;

    /**
     * stop_reason
     */
    private String stop_reason;

}
