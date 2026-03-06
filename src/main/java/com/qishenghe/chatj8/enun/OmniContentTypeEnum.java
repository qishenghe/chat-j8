package com.qishenghe.chatj8.enun;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:10
 * @since 2026/3/5 19:10 by qishenghe for init
 */
public enum OmniContentTypeEnum {

    /**
     * text
     */
    text("text", "文本类型", "文本类型"),

    /**
     * image_url
     */
    image_url("image_url", "图片类型", "图片类型");

    private String code;

    private String name;

    private String note;

    OmniContentTypeEnum(String code, String name, String note) {
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

    public static OmniContentTypeEnum get(String code) {
        for (OmniContentTypeEnum single : values()) {
            if (single.code.equals(code)) {
                return single;
            }
        }
        return null;
    }

}
