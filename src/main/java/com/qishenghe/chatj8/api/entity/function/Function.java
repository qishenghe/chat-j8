package com.qishenghe.chatj8.api.entity.function;

import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:23
 * @since 2026/3/5 19:23 by qishenghe for init
 */
@Data
public class Function implements Serializable {

    private static final long serialVersionUID = 6705279975884921211L;

    /**
     * 函数名
     */
    private String name;

    /**
     * 函数描述
     */
    private String description;

    /**
     * 参数
     */
    private Parameters parameters;

}
