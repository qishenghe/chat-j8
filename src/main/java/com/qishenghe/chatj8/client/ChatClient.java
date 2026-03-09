package com.qishenghe.chatj8.client;

import com.alibaba.fastjson.TypeReference;
import com.qishenghe.chatj8.api.ChatApiUtil;
import com.qishenghe.chatj8.api.entity.Choice;
import com.qishenghe.chatj8.api.entity.Message;
import com.qishenghe.chatj8.api.request.CompletionsRequest;
import com.qishenghe.chatj8.api.response.CompletionsResponse;
import com.qishenghe.chatj8.api.sse.listener.ChatListener;
import com.qishenghe.chatj8.api.sse.pro.EventSourceProcessor;
import com.qishenghe.chatj8.client.entity.*;
import com.qishenghe.chatj8.client.entity.spec.ChatRequestSpec;
import com.qishenghe.chatj8.enun.ChatRoleEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 14:05
 * @since 2026/3/6 14:05 by qishenghe for init
 */
public class ChatClient {

    /**
     * api util
     */
    private ChatApiUtil util;

    /**
     * client config
     */
    private ClientConfig config;

    /**
     * chat client
     *
     * @param util util
     * @param config config
     * @author qishenghe
     * @date 2026/3/6 15:14
     */
    private ChatClient (ChatApiUtil util, ClientConfig config) {
        this.util = util;
        this.config = config;
    }

    /**
     * completions
     *
     * @param param param
     * @return result
     * @author qishenghe
     * @date 2026/3/6 15:33
     */
    public ChatResult completions (ChatParam param) {

        CompletionsRequest request = processRequest(param);

        CompletionsResponse response = util.completions(request);

        return processResponse(response);
    }

    /**
     * completions
     *
     * @param param param
     * @author qishenghe
     * @date 2026/3/6 15:33
     */
    public void completions (ChatParam param, ChatListener<ChatResult> listener) {

        CompletionsRequest request = processRequest(param);

        listener.setProcessor(new EventSourceProcessor<ChatResult>() {
            @Override
            public ChatResult process(String data) {
                CompletionsResponse response = util.processResult(data, new TypeReference<CompletionsResponse>() {});
                return processResponse(response);
            }
        });

        util.completionsStream(request, listener);
    }

    /**
     * process request
     *
     * @param param param
     * @return result
     * @author qishenghe
     * @date 2026/3/6 15:18
     */
    private CompletionsRequest processRequest (ChatParam param) {

        if (param.getThinkAble() == null) {
            param.setThinkAble(true);
        }

        CompletionsRequest request = new CompletionsRequest();

        List<Message> msgList = new ArrayList<>();

        if (param.getMessages() != null && !param.getMessages().isEmpty()) {
            for (ChatMessage single : param.getMessages()) {
                Message msg = new Message();
                msg.setRole(single.getRole());
                msg.setContent(single.getContent());
                msg.setTool_calls(single.getToolCalls());

                msgList.add(msg);
            }
        }

        request.setMessages(msgList);

        // model
        request.setModel(config.getModel());
        // temp
        request.setTemperature(config.getTemperature());
        // top p
        request.setTop_p(config.getTopP());
        // pres
        request.setPresence_penalty(config.getPresencePenalty());
        // freq
        request.setFrequency_penalty(config.getFrequencyPenalty());
        // seed
//        request.setSeed();
        // max token
//        request.setMax_tokens();
        // tools
        request.setTools(param.getTools());

        // think able
        CompletionsRequest.ChatTemplateKwargs kwargs = new CompletionsRequest.ChatTemplateKwargs();
        kwargs.setEnable_thinking(param.getThinkAble());
        request.setChat_template_kwargs(kwargs);

        return request;
    }

    /**
     * process response
     *
     * @param response response
     * @return result
     * @author qishenghe
     * @date 2026/3/6 15:25
     */
    private ChatResult processResponse (CompletionsResponse response) {

        ChatResult result = null;

        if (response != null) {
            result = new ChatResult();
            result.setId(response.getId());

            List<ChatChoice> choices = new ArrayList<>();

            if (response.getChoices() != null && !response.getChoices().isEmpty()) {
                for (Choice single : response.getChoices()) {
                    ChatChoice singleChoice = new ChatChoice();
                    singleChoice.setIndex(single.getIndex());

                    if (single.getMessage() != null) {
                        ChatMessage singleMessage = new ChatMessage();
                        singleMessage.setContent(single.getMessage().getContent());
                        singleMessage.setRole(single.getMessage().getRole());
                        singleMessage.setToolCalls(single.getMessage().getTool_calls());

                        singleChoice.setMessage(singleMessage);
                    }

                    if (single.getDelta() != null) {
                        ChatMessage singleMessage = new ChatMessage();
                        singleMessage.setContent(single.getDelta().getContent());
                        singleMessage.setRole(single.getDelta().getRole());
                        // TODO stream ?
                        singleMessage.setToolCalls(single.getDelta().getTool_calls());

                        singleChoice.setDelta(singleMessage);
                    }

                    choices.add(singleChoice);
                }
            }

            result.setChoices(choices);
        }

        return result;
    }

    /**
     * prompt
     *
     * @return result
     * @author qishenghe
     * @date 2026/3/6 16:36
     */
    public ChatRequestSpec prompt () {

        ChatRequestSpec result = new ChatRequestSpec();
        result.setChatClient(this);

        ChatParam chatParam = new ChatParam();
        chatParam.setMessages(new ArrayList<>());
        chatParam.setTools(new ArrayList<>());

        result.setChatParam(chatParam);

        return result;
    }

    /**
     * prompt
     *
     * @param content content
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:24
     */
    public ChatRequestSpec prompt (String content) {

        ChatRequestSpec result = prompt();

        if (content != null) {
            ChatMessage sys = new ChatMessage();
            sys.setContent(content);
            sys.setRole(ChatRoleEnum.SYSTEM.getCode());

            result.getChatParam().getMessages().add(sys);
        }

        return result;
    }

    /**
     * prompt
     *
     * @param context context
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:22
     */
    public ChatRequestSpec prompt (List<ChatMessage> context) {

        ChatRequestSpec result = prompt();

        if (context != null && !context.isEmpty()) {
            result.getChatParam().getMessages().addAll(context);
        }

        return result;
    }

    /**
     * builder
     *
     * @return result
     * @author qishenghe
     * @date 2026/3/6 15:39
     */
    public static Builder builder () {
        return new Builder();
    }

    /**
     * builder
     *
     * @author qishenghe
     * @date 2026/3/6 14:58
     */
    @Data
    public static class Builder {

        /**
         * base url（require）
         */
        private String baseUrl;

        /**
         * api key
         */
        private String apiKey;

        /**
         * username
         */
        private String username;

        /**
         * password
         */
        private String password;

        /**
         * api path
         */
        private String apiPath;

        /**
         * model（require）
         */
        private String model;

        /**
         * temp
         */
        private Double temperature;

        /**
         * top p
         */
        private Double topP;

        /**
         * presencePenalty
         */
        private Double presencePenalty;

        /**
         * frequencyPenalty
         */
        private Double frequencyPenalty;

        /**
         * base url
         *
         * @param baseUrl baseUrl
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:03
         */
        public Builder baseUrl (String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * api key
         *
         * @param apiKey apiKey
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:04
         */
        public Builder apiKey (String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        /**
         * access
         *
         * @param username username
         * @param password password
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:06
         */
        public Builder access(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        /**
         * api path
         *
         * @param apiPath apiPath
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:07
         */
        public Builder apiPath (String apiPath) {
            this.apiPath = apiPath;
            return this;
        }

        /**
         * model
         *
         * @param model model
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:08
         */
        public Builder model (String model) {
            this.model = model;
            return this;
        }

        /**
         * temp
         *
         * @param temperature temperature
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:08
         */
        public Builder temperature (Double temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * top p
         *
         * @param topP topP
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:09
         */
        public Builder topP (Double topP) {
            this.topP = topP;
            return this;
        }

        /**
         * presence penalty
         *
         * @param presencePenalty presencePenalty
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:09
         */
        public Builder presencePenalty (Double presencePenalty) {
            this.presencePenalty = presencePenalty;
            return this;
        }

        /**
         * frequency penalty
         *
         * @param frequencyPenalty frequencyPenalty
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:10
         */
        public Builder frequencyPenalty (Double frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        /**
         * build
         *
         * @return result
         * @author qishenghe
         * @date 2026/3/6 15:14
         */
        public ChatClient build () {

            if (baseUrl == null || baseUrl.isEmpty()) {
                throw new RuntimeException("baseUrl is empty");
            }

            ChatApiUtil util = new ChatApiUtil();
            util.setBaseUrl(baseUrl);
            util.setApiKey(apiKey);
            util.setUsername(username);
            util.setPassword(password);
            if (apiPath != null && !apiPath.isEmpty()) {
                util.setApiPath(apiPath);
            }

            ClientConfig config = new ClientConfig();
            config.setModel(model);
            config.setTemperature(temperature);
            config.setTopP(topP);
            config.setPresencePenalty(presencePenalty);
            config.setFrequencyPenalty(frequencyPenalty);

            ChatClient chatClient = new ChatClient(util, config);

            return chatClient;
        }

    }

}
