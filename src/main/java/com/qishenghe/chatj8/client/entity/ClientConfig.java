package com.qishenghe.chatj8.client.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 14:06
 * @since 2026/3/6 14:06 by qishenghe for init
 */
@Data
public class ClientConfig implements Serializable {

    private static final long serialVersionUID = -1572018520729915467L;

    /**
     * model
     */
    private String model;

    /**
     * temperature
     */
    private Double temperature;

    /**
     * topP
     */
    private Double topP;

    /**
     * presencePenalty
     */
    private Double presencePenalty;

    /**
     * frequencyPenalty
     */
    private Double frequencyPenalty;

}
