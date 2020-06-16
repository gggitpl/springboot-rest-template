package com.gggitpl.template.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author gggitpl
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    /**
     * 错误状态码
     */
    private final GlobalServiceCode code;

    /**
     * 时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * 消息
     */
    private String message;

    /**
     * 调试信息
     */
    private final String debugMessage;

    /**
     *
     */
    private List<? extends AbstractApiSubError> errors;

    public ApiError(GlobalServiceCode globalServiceCode, String debugMessage) {
        this.code = globalServiceCode;
        this.debugMessage = debugMessage;
        this.message = globalServiceCode.getText();
    }

    public ApiError(GlobalServiceCode globalServiceCode, String debugMessage, List<? extends AbstractApiSubError> errors) {
        this.code = globalServiceCode;
        this.debugMessage = debugMessage;
        this.message = globalServiceCode.getText();
        this.errors = errors;
    }

}
