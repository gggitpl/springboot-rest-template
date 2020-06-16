package com.gggitpl.template.filter;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @author gggitpl
 */
@RequiredArgsConstructor
@JsonFilter("com.gggitpl.template.filter.FieldFilter")
public class FieldFilter extends SimpleBeanPropertyFilter implements Serializable {

    private final FieldFilterResponseBodyWrapper bodyWrapper;

    @Override
    protected boolean include(BeanPropertyWriter writer) {
        return super.include(writer);
    }

    @Override
    protected boolean include(PropertyWriter writer) {
        return bodyWrapper.include(writer);
    }
}
