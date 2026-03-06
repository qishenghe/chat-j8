package com.qishenghe.chatj8.enun;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:02
 * @since 2026/3/5 19:02 by qishenghe for init
 */
public enum ChatRoleEnum {

    /**
     * system
     */
    SYSTEM("system", "系统", "提供系统指令或设置对话的上下文"),

    /**
     * user
     */
    USER("user", "用户", "表示用户的输入"),

    /**
     * assistant
     */
    ASSISTANT("assistant", "助手", "表示助手的回复"),

    /**
     * tool
     */
    TOOL("tool", "工具调用", "用于声明工具调用");

    private String code;

    private String name;

    private String note;

    ChatRoleEnum(String code, String name, String note) {
        this.code = code;
        this.name = name;
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public static ChatRoleEnum get(String code) {
        for (ChatRoleEnum single : values()) {
            if (single.code.equals(code)) {
                return single;
            }
        }
        return null;
    }

}
