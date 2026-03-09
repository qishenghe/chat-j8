package com.qishenghe.chatj8.demo;

import com.qishenghe.chatj8.client.tool.annotation.Tool;
import com.qishenghe.chatj8.client.tool.annotation.ToolParam;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 19:04
 * @since 2026/3/6 19:04 by qishenghe for init
 */
public class TestTools {

    @Tool(description = "get target city today weather")
    public String getWeather (@ToolParam(name = "cityName", required = true, description = "city name") String cityName) {

        return "-21℃";
    }

}
