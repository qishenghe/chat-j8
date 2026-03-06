package com.qishenghe.chatj8.api.entity.function;

import com.alibaba.fastjson.JSONObject;
import com.qishenghe.chatj8.enun.ParameterTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:24
 * @since 2026/3/5 19:24 by qishenghe for init
 */
@Data
public class Parameters implements Serializable {

    private static final long serialVersionUID = 5749227445700781449L;

    /**
     * 参数类型
     * {@link ParameterTypeEnum}
     */
    private String type;

    /**
     * 属性
     */
    private JSONObject properties;

    /**
     * 必填参数
     */
    private List<String> required;

}
