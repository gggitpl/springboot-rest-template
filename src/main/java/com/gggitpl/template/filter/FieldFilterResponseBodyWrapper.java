package com.gggitpl.template.filter;

import com.fasterxml.jackson.databind.ser.PropertyWriter;
import lombok.Data;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author gggitpl
 */
@Data
public class FieldFilterResponseBodyWrapper {

    private final Object object;

    private final MethodParameter returnType;

    public FieldFilterResponseBodyWrapper(Object object, MethodParameter returnType) {
        this.object = object;
        this.returnType = returnType;
    }

    public String cacheKey() {
        return returnType.getDeclaringClass().getCanonicalName() + "#" + Objects.requireNonNull(returnType.getMethod()).getName();
    }

    public boolean include(PropertyWriter writer) {
        final String fieldName = writer.getName();
        final Class<?> aClass = writer.getMember().getDeclaringClass();
        final Method method = returnType.getMethod();
        if (Objects.nonNull(method)) {
            if (method.isAnnotationPresent(FieldFiltering.class)) {
                final FieldFiltering fieldFiltering = method.getAnnotation(FieldFiltering.class);
                final List<String> fields = Arrays.asList(fieldFiltering.fields());
                return fieldFiltering.keep() == fields.contains(fieldName);
            } else if (method.isAnnotationPresent(FieldFilters.class)) {
                final FieldFilters fieldFilters = method.getAnnotation(FieldFilters.class);
                final Map<Class<?>, FieldFiltering> classFieldFilteringMap = Arrays.stream(fieldFilters.value()).collect(Collectors.toMap(FieldFiltering::aClass, fieldFiltering -> fieldFiltering));
                if (classFieldFilteringMap.containsKey(aClass)) {
                    final FieldFiltering fieldFiltering = classFieldFilteringMap.get(aClass);
                    final List<String> fields = Arrays.asList(fieldFiltering.fields());
                    return fieldFiltering.keep() == fields.contains(fieldName);
                }
            }
        }
        return true;
    }
}
