package com.gggitpl.template.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 全局服务码
 *
 * @author gggitpl
 */
@Getter
@RequiredArgsConstructor
public enum GlobalServiceCode {

    /**
     *
     */
    METHOD_ARGUMENT_NOT_VALID(10001, "方法参数验证错误"),
    BIND_EXCEPTION(10002, "参数绑定错误"),
    REQUEST_ERROR(10003, "请求错误"),
    INTERNAL_SERVER_ERROR(10004, "服务器内部错误"),
    ;
    @JsonValue
    private final Integer value;
    private final String text;

}
