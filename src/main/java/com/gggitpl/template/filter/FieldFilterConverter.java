package com.gggitpl.template.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author gggitpl
 */
@SuppressWarnings("NullableProblems")
@Component
public class FieldFilterConverter extends AbstractHttpMessageConverter<Object> {

    @Lazy
    @Resource
    public ObjectMapper objectMapper;
    @Resource
    public CacheObjectMapper cacheObjectMapper;

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (o instanceof FieldFilterResponseBodyWrapper) {
            final FieldFilterResponseBodyWrapper fieldFilterResponseBodyWrapper = (FieldFilterResponseBodyWrapper) o;
            final ObjectMapper objectMapper = cacheObjectMapper.getObjectMapper(this.objectMapper, fieldFilterResponseBodyWrapper);
            objectMapper.writeValue(outputMessage.getBody(), fieldFilterResponseBodyWrapper.getObject());
        } else {
            objectMapper.writeValue(outputMessage.getBody(), o);
        }
    }

    @Override
    protected boolean canWrite(MediaType mediaType) {
        return true;
    }

}
