package com.qishenghe.chatj8.api.entity.function;

import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:28
 * @since 2026/3/5 19:28 by qishenghe for init
 */
@Data
public class CallsFunction implements Serializable {

    private static final long serialVersionUID = 7925556922410965365L;

    /**
     * function name
     */
    private String name;

    /**
     * arguments（JSON STR）
     */
    private String arguments;

}
