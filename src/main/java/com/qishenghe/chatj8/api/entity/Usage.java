package com.qishenghe.chatj8.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:31
 * @since 2026/3/5 19:31 by qishenghe for init
 */
@Data
public class Usage implements Serializable {

    private static final long serialVersionUID = -6175180303602546645L;

    /**
     * prompt_tokens
     */
    private Integer prompt_tokens;

    /**
     * total_tokens
     */
    private Integer total_tokens;

    /**
     * completion_tokens
     */
    private Integer completion_tokens;

    /**
     * prompt_tokens_details
     */
    private Object prompt_tokens_details;

}
