package com.qishenghe.chatj8.client.tool.annotation;

import java.lang.annotation.*;

/**
 * chat-j8
 *
 * @author qishenghe
 * @date 2026/3/6 18:50
 * @since 2026/3/6 18:50 by qishenghe for init
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tool {

    String name() default "";

    String description() default "";

}
