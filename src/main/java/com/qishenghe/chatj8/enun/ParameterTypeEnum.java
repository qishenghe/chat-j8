package com.qishenghe.chatj8.enun;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:24
 * @since 2026/3/5 19:24 by qishenghe for init
 */
public enum ParameterTypeEnum {

    /**
     * 字符串
     */
    type_string("string", "字符串", "字符串"),

    /**
     * 整数
     */
    type_integer("integer", "整数", "整数"),

    /**
     * 浮点数或任意数字
     */
    type_number("number", "浮点数或任意数字", "浮点数或任意数字"),

    /**
     * 布尔
     */
    type_boolean("boolean", "布尔", "布尔"),

    /**
     * object
     */
    type_object("object", "对象", "对象");

    private String code;

    private String name;

    private String note;

    ParameterTypeEnum(String code, String name, String note) {
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

    public static ParameterTypeEnum get(String code) {
        for (ParameterTypeEnum single : values()) {
            if (single.code.equals(code)) {
                return single;
            }
        }
        return null;
    }

}
