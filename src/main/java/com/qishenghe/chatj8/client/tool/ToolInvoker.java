package com.qishenghe.chatj8.client.tool;

import com.alibaba.fastjson.JSONObject;
import com.qishenghe.chatj8.api.entity.function.ToolCall;
import com.qishenghe.chatj8.client.tool.annotation.ToolParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 18:57
 * @since 2026/3/6 18:57 by qishenghe for init
 */
public class ToolInvoker {

    /**
     * invoke tool
     *
     * @param clazz clazz
     * @param toolCall toolCall
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:58
     */
    public static Object invokeTool (Class<?> clazz, ToolCall toolCall) {
        return invokeTool(clazz, toolCall, null);
    }

    /**
     * invoke tool
     *
     * @param clazz clazz
     * @param toolCall toolCall
     * @param instance instance
     * @return result
     * @author qishenghe
     * @date 2026/3/6 18:59
     */
    public static Object invokeTool (Class<?> clazz, ToolCall toolCall, Object instance) {

        Object result;

        if (toolCall != null && toolCall.getFunction() != null && toolCall.getFunction().getName() != null && !toolCall.getFunction().getName().isEmpty()) {

            Method[] methods = clazz.getMethods();

            Method method = null;
            for (Method single : methods) {
                if (single.getName().equals(toolCall.getFunction().getName())) {
                    method = single;
                    break;
                }
            }

            if (method == null) {
                throw new RuntimeException("未匹配到可使用的函数");
            }

            JSONObject arguments = JSONObject.parseObject(toolCall.getFunction().getArguments());

            result = invokeTool(method, instance, arguments);

        } else {
            throw new RuntimeException("tool call定义不合法，无法调用");
        }

        return result;
    }

    /**
     * invoke tool
     *
     * @param method method
     * @param instance 指定代理调用实例（为空时会尝试无参构造）
     * @param arguments arguments
     * @return result
     * @author qishenghe
     * @date 2025/12/11 17:49
     */
    public static Object invokeTool(Method method, Object instance, JSONObject arguments) {

        if (!Modifier.isStatic(method.getModifiers()) && instance == null) {
            // 非静态方法，需要实例
            try {
                instance = method.getDeclaringClass().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("非静态方法，代理调用构建实例时失败，原因：" + e.getMessage());
            }
        }

        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            ToolParam annotation = parameters[i].getAnnotation(ToolParam.class);
            if (annotation == null) {
                continue;
            }

            String paramName = annotation.name();
            Class<?> paramType = parameters[i].getType();

            Object value = null;

            if (arguments != null) {
                value = arguments.get(paramName);
            }

            // 处理缺失值
            if (value == null) {
                if (annotation.required()) {
                    throw new RuntimeException("方法调用时异常，缺少必填参数: " + paramName);
                } else {
                    // 获取默认值
                    args[i] = getDefaultValue(paramType);
                    continue;
                }
            }

            // 类型转换
            args[i] = convertValue(value, paramType);
        }

        Object result = null;

        try {

            if (Modifier.isStatic(method.getModifiers())) {
                // 静态方法
                result = method.invoke(null, args);
            } else {
                // 非静态
                result = method.invoke(instance, args);
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * default value
     *
     * @param type type
     * @return result
     * @author qishenghe
     * @date 2025/12/11 17:42
     */
    private static Object getDefaultValue(Class<?> type) {
        if (type == boolean.class) {
            return false;
        }
        if (type == byte.class) {
            return (byte) 0;
        }
        if (type == char.class) {
            return '\0';
        }
        if (type == short.class) {
            return (short) 0;
        }
        if (type == int.class) {
            return 0;
        }
        if (type == long.class) {
            return 0L;
        }
        if (type == float.class) {
            return 0.0f;
        }
        if (type == double.class) {
            return 0.0;
        }
        return null;
    }

    /**
     * convert value
     *
     * @param value value
     * @param targetType targetType
     * @return result
     * @author qishenghe
     * @date 2025/12/11 17:42
     */
    private static Object convertValue(Object value, Class<?> targetType) {
        if (targetType == String.class) {
            return value.toString();
        } else if (targetType == Integer.class || targetType == int.class) {
            return ((Number) value).intValue();
        } else if (targetType == Long.class || targetType == long.class) {
            return ((Number) value).longValue();
        } else if (targetType == Double.class || targetType == double.class) {
            return ((Number) value).doubleValue();
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return value instanceof Boolean ? value : Boolean.parseBoolean(value.toString());
        } else if (targetType.isEnum()) {
            return Enum.valueOf((Class<Enum>) targetType, value.toString());
        } else {
            throw new RuntimeException("不支持的参数类型: " + targetType);
        }
    }

}
