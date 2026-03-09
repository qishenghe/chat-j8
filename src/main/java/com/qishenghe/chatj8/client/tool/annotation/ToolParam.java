package com.qishenghe.chatj8.client.tool.annotation;

import java.lang.annotation.*;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 18:54
 * @since 2026/3/6 18:54 by qishenghe for init
 */
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ToolParam {

    String name();

    boolean required() default false;

    String description() default "";

}
