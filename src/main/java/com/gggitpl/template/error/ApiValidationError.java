package com.gggitpl.template.error;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gggitpl
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ApiValidationError extends AbstractApiSubError {

    /**
     * 对象
     */
    private String object;

    /**
     * 字段
     */
    private String field;

    /**
     * 值
     */
    private Object rejectedValue;

    /**
     * 消息
     */
    private String message;

}
