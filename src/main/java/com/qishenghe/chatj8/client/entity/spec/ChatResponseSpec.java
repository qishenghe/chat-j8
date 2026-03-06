package com.qishenghe.chatj8.client.entity.spec;

import com.qishenghe.chatj8.client.entity.ChatResult;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 18:06
 * @since 2026/3/6 18:06 by qishenghe for init
 */
public class ChatResponseSpec {

    /**
     * chat result
     */
    private ChatResult chatResult;

    public ChatResponseSpec (ChatResult chatResult) {
        this.chatResult = chatResult;
    }

    /**
     * chat result
     *
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:13
     */
    public ChatResult getResult () {
        return this.chatResult;
    }

    /**
     * content
     *
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:15
     */
    public String content () {

        Object content = chatResult.getChoices().get(0).getMessage().getContent();

        String result = null;

        if (content != null) {
            if (content instanceof String) {
                result = (String) content;
            } else {
                throw new RuntimeException("Multimodal output of non-String types is not supported");
            }
        }

        return result;
    }

}
