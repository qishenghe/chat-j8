package com.qishenghe.chatj8.api.entity.function;

import com.qishenghe.chatj8.enun.ToolTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:08
 * @since 2026/3/5 19:08 by qishenghe for init
 */
@Data
public class ToolCall implements Serializable {

    private static final long serialVersionUID = 6697032053562988494L;

    /**
     * id
     */
    private String id;

    /**
     * type
     * {@link ToolTypeEnum}
     */
    private String type;

    /**
     * function
     */
    private CallsFunction function;

}
