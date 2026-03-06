package com.qishenghe.chatj8.client.entity;

import com.qishenghe.chatj8.api.entity.content.OmniContent;
import com.qishenghe.chatj8.api.entity.function.ToolCall;
import com.qishenghe.chatj8.enun.ChatRoleEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 14:15
 * @since 2026/3/6 14:15 by qishenghe for init
 */
@Data
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 7557191228388959975L;

    /**
     * role
     * {@link ChatRoleEnum}
     */
    private String role;

    /**
     * content
     */
    private Object content;

    /**
     * tool call id
     */
    private String toolCallId;

    /**
     * tool calls
     */
    private List<ToolCall> toolCalls;

    /**
     * builder
     *
     * @return result
     * @author qishenghe
     * @date 2026/3/6 14:35
     */
    public static Builder builder () {
        return new Builder();
    }

    /**
     * builder
     *
     * @author qishenghe
     * @date 2026/3/6 14:19
     */
    @Data
    public static class Builder implements Serializable{

        private static final long serialVersionUID = 6152343590769110657L;

        private final List<ChatMessage> MESSAGES = new ArrayList<>();

        /**
         * add
         *
         * @param role role
         * @param content content
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:21
         */
        public Builder add (String role, Object content) {

            ChatMessage message = new ChatMessage();
            message.setRole(role);
            message.setContent(content);

            MESSAGES.add(message);

            return this;
        }

        /**
         * add
         *
         * @param role role
         * @param content content
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:25
         */
        public Builder add (ChatRoleEnum role, String content) {

            return add(role.getCode(), content);
        }

        /**
         * add
         *
         * @param role role
         * @param omniContents omniContents
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:28
         */
        public Builder add (ChatRoleEnum role, List<OmniContent> omniContents) {

            return add(role.getCode(), omniContents);
        }

        /**
         * add all
         *
         * @param messages messages
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:29
         */
        public Builder addAll (List<ChatMessage> messages) {

            if (messages != null) {
                MESSAGES.addAll(messages);
            }
            return this;
        }

        /**
         * user
         *
         * @param content content
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:30
         */
        public Builder user (String content) {
            return add(ChatRoleEnum.USER, content);
        }

        /**
         * system
         *
         * @param content content
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:31
         */
        public Builder system (String content) {
            return add(ChatRoleEnum.SYSTEM, content);
        }
        
        /**
         * assistant
         * 
         * @param content content
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:32
         */
        public Builder assistant (String content) {
            return add(ChatRoleEnum.ASSISTANT, content);
        }

        /**
         * tool
         *
         * @param content content
         * @param toolCallId toolCallId
         * @return result
         * @author qishenghe
         * @date 2026/3/6 14:33
         */
        public Builder tool (String content, String toolCallId) {
            ChatMessage message = new ChatMessage();

            message.setRole(ChatRoleEnum.TOOL.getCode());
            message.setContent(content);
            message.setToolCallId(toolCallId);

            MESSAGES.add(message);

            return this;
        }

        /**
         * build
         *
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:59
         */
        public List<ChatMessage> build () {
            return new ArrayList<>(MESSAGES);
        }

    }

}
