package com.qishenghe.chatj8.api.response;

import com.qishenghe.chatj8.api.entity.Choice;
import com.qishenghe.chatj8.api.entity.Usage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:29
 * @since 2026/3/5 19:29 by qishenghe for init
 */
@Data
public class CompletionsResponse implements Serializable {

    private static final long serialVersionUID = -7606905211058931057L;

    /**
     * id
     */
    private String id;

    /**
     * object
     */
    private String object;

    /**
     * created
     */
    private Long created;

    /**
     * model
     */
    private String model;

    /**
     * choices
     */
    private List<Choice> choices;

    /**
     * usage
     */
    private Usage usage;

    /**
     * prompt_logprobs
     */
    private Object prompt_logprobs;

}
