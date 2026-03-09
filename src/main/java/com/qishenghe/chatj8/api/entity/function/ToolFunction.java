package com.qishenghe.chatj8.api.entity.function;

import com.qishenghe.chatj8.enun.ToolTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:22
 * @since 2026/3/5 19:22 by qishenghe for init
 */
@Data
public class ToolFunction implements Serializable {

    private static final long serialVersionUID = -6034144972324519507L;

    /**
     * type
     * {@link ToolTypeEnum}
     */
    private String type;

    /**
     * function
     */
    private Function function;

}
