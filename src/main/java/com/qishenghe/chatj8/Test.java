package com.qishenghe.chatj8;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qishenghe.chatj8.api.ChatApiUtil;
import com.qishenghe.chatj8.api.entity.Message;
import com.qishenghe.chatj8.api.request.CompletionsRequest;
import com.qishenghe.chatj8.api.response.CompletionsResponse;
import com.qishenghe.chatj8.enun.ChatRoleEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 */
public class Test {

    public static void main(String[] args) {

        String baseUrl = "http://192.168.205.143:7000";
        String model = "Qwen/Qwen3.5-35B-A3B";

        ChatApiUtil util = new ChatApiUtil();

        util.setBaseUrl(baseUrl);

        CompletionsRequest request = new CompletionsRequest();
        request.setModel(model);

        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        message.setRole(ChatRoleEnum.USER.getCode());
        message.setContent("你好");
        messages.add(message);

        request.setMessages(messages);

        CompletionsResponse completions = util.completions(request);

        System.out.println();
    }

}
