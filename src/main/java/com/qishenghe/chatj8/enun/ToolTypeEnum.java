package com.qishenghe.chatj8.enun;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:08
 * @since 2026/3/5 19:08 by qishenghe for init
 */
public enum ToolTypeEnum {

    /**
     * function
     */
    function("function", "函数", "工具类型");

    private String code;

    private String name;

    private String note;

    ToolTypeEnum(String code, String name, String note) {
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

    public static ToolTypeEnum get(String code) {
        for (ToolTypeEnum single : values()) {
            if (single.code.equals(code)) {
                return single;
            }
        }
        return null;
    }

}
