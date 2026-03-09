package com.qishenghe.chatj8.client.tool;

import com.alibaba.fastjson.JSONObject;
import com.qishenghe.chatj8.api.entity.function.Function;
import com.qishenghe.chatj8.api.entity.function.Parameters;
import com.qishenghe.chatj8.api.entity.function.ToolFunction;
import com.qishenghe.chatj8.client.tool.annotation.Tool;
import com.qishenghe.chatj8.client.tool.annotation.ToolParam;
import com.qishenghe.chatj8.enun.ParameterTypeEnum;
import com.qishenghe.chatj8.enun.ToolTypeEnum;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 18:49
 * @since 2026/3/6 18:49 by qishenghe for init
 */
public class ToolBuilder {

    /**
     * build tools
     *
     * @param clazz clazz
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:55
     */
    public static List<ToolFunction> buildTools(Class<?> clazz) {
        List<ToolFunction> result = new ArrayList<>();

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            Tool mctTool = method.getAnnotation(Tool.class);
            if (mctTool == null) {
                continue;
            }

            String toolName = mctTool.name().isEmpty() ? method.getName() : mctTool.name();
            String description = mctTool.description();

            // 获取参数
            Parameters parameters = buildParametersSchema(method);

            Function singleFunction = new Function();
            singleFunction.setName(toolName);
            singleFunction.setDescription(description);
            singleFunction.setParameters(parameters);

            ToolFunction singleTool = new ToolFunction();
            singleTool.setType(ToolTypeEnum.function.getCode());
            singleTool.setFunction(singleFunction);

            result.add(singleTool);
        }

        return result;
    }

    /**
     * build parameters
     *
     * @param method method
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:55
     */
    private static Parameters buildParametersSchema(Method method) {

        Parameters result = new Parameters();

        result.setType(ParameterTypeEnum.type_object.getCode());

        JSONObject properties = new JSONObject();
        List<String> required = new ArrayList<>();

        Parameter[] parameters = method.getParameters();
        for (Parameter param : parameters) {

            ToolParam paramAnnotation = param.getAnnotation(ToolParam.class);

            if (paramAnnotation == null) {
                // 跳过未标注的参数（要求显式声明）
                continue;
            }

            String paramName = paramAnnotation.name();
            String desc = paramAnnotation.description();

            // 推断 JSON 类型
            ParameterTypeEnum jsonType = inferJsonType(param.getType());

            JSONObject prop = new JSONObject();
            prop.put("type", jsonType.getCode());
            if (!desc.isEmpty()) {
                prop.put("description", desc);
            }

            properties.put(paramName, prop);

            if (paramAnnotation.required()) {
                required.add(paramName);
            }
        }

        result.setProperties(properties);
        if (!required.isEmpty()) {
            result.setRequired(required);
        }

        return result;
    }

    /**
     * infer json type
     *
     * @param type type
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:55
     */
    private static ParameterTypeEnum inferJsonType(Class<?> type) {
        if (type == String.class) {
            return ParameterTypeEnum.type_string;
        } else if (type == Integer.class || type == int.class) {
            return ParameterTypeEnum.type_integer;
        } else if (type == Long.class || type == long.class) {
            return ParameterTypeEnum.type_integer;
        } else if (type == Double.class || type == double.class || type == Float.class || type == float.class) {
            return ParameterTypeEnum.type_number;
        } else if (type == Boolean.class || type == boolean.class) {
            return ParameterTypeEnum.type_boolean;
        } else if (type == Object.class) {
            return ParameterTypeEnum.type_object;
        } else {
            // 其他复杂类型默认视为 object（或可抛异常）
            return ParameterTypeEnum.type_object;
        }
    }

}
