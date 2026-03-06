package com.qishenghe.chatj8.api.request;

import com.qishenghe.chatj8.api.entity.Message;
import com.qishenghe.chatj8.api.entity.function.Tool;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/5 19:00
 * @since 2026/3/5 19:00 by qishenghe for init
 */
@Data
public class CompletionsRequest implements Serializable {

    private static final long serialVersionUID = -6490677160193902844L;

    /**
     * model
     */
    private String model;

    /**
     * messages
     */
    private List<Message> messages;

    /**
     * tools
     */
    private List<Tool> tools;

    /**
     * 温度
     * （通常为0 ~ 1之间的浮点数）
     * （越接近0：输出更加集中与确定。越接近1：输出越随机）
     * 参考：https://platform.openai.com/docs/api-reference/completions/create
     */
    private Double temperature;

    /**
     * 核采样率
     * （0 ~ 1之间的浮点数）
     * （温度采样的替代方案，可同时使用，模型将考虑具有topP概率质量的标记结果）
     * （例：0.1表示仅考虑前10%概率质量的内容）
     * 参考：https://platform.openai.com/docs/api-reference/completions/create
     */
    private Double top_p;

    /**
     * 停止词
     * （当遇到指定字符序列时，将停止生成，注：返回内容不包含停止序列）
     */
    private List<String> stop;

    /**
     * 种子
     * （如果指定种子，将尽最大努力确定性的采样，以便具有相同参数的请求能够返回相同结果，注：不保证确定性）
     */
    private Integer seed;

    /**
     * 最大token数量
     * （注：用于限制返回内容【completion_tokens】的最大token数量）
     */
    private Integer max_tokens;

    /**
     * presence penalty
     */
    private Double presence_penalty;

    /**
     * frequency penalty
     */
    private Double frequency_penalty;

    /**
     * chat template kwargs
     */
    private ChatTemplateKwargs chat_template_kwargs;

    /**
     * chat_template_kwargs
     *
     * @author qishenghe
     * @date 2025/4/30 10:00
     */
    @Data
    public static class ChatTemplateKwargs {

        /**
         * enable thinking
         */
        private Boolean enable_thinking;

    }

}
