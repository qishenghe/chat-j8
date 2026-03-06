package com.qishenghe.chatj8.api.entity;

import com.qishenghe.chatj8.api.entity.content.OmniContent;
import com.qishenghe.chatj8.api.entity.function.ToolCall;
import com.qishenghe.chatj8.enun.ChatRoleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:01
 * @since 2026/3/5 19:01 by qishenghe for init
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 3046021896931310082L;

    /**
     * 角色
     * {@link ChatRoleEnum}
     */
    private String role;

    /**
     * 内容
     * 文本：{@link Message#setContent(String)}
     * 多模态：{@link Message#setContent(List)}
     */
    private Object content;

    /**
     * tool calls
     */
    private List<ToolCall> tool_calls;

    public void setContent(String content) {
        this.content = content;
    }

    public void setContent(List<OmniContent> content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
