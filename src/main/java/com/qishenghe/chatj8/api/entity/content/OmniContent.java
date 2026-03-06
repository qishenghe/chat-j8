package com.qishenghe.chatj8.api.entity.content;

import com.qishenghe.chatj8.enun.OmniContentTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:10
 * @since 2026/3/5 19:10 by qishenghe for init
 */
@Data
public class OmniContent implements Serializable {

    private static final long serialVersionUID = -6498466464922881698L;

    /**
     * type
     * {@link OmniContentTypeEnum}
     */
    private String type;

    /**
     * 图片情况
     */
    private ImageUrl image_url;

    /**
     * 文本情况
     */
    private String text;

    /**
     * image
     *
     * @author qishenghe
     * @date 2026/3/5 19:16
     */
    @Data
    public static class ImageUrl implements Serializable {

        private static final long serialVersionUID = 6777083381593945932L;

        /**
         * url
         */
        private String url;

    }

}
