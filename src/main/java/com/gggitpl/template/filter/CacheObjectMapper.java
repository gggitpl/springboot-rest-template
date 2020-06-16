package com.gggitpl.template.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gggitpl
 */
@Component
public class CacheObjectMapper {

    private final ConcurrentHashMap<String, ObjectMapper> concurrentHashMap = new ConcurrentHashMap<>();

    public ObjectMapper getObjectMapper(ObjectMapper objectMapper, FieldFilterResponseBodyWrapper bodyWrapper) {
        return Optional.ofNullable(concurrentHashMap.get(bodyWrapper.cacheKey()))
                .orElseGet(() -> {
                    final ObjectMapper copyObjectMapper = objectMapper.copy();
                    copyObjectMapper.addMixIn(Object.class, FieldFilter.class);
                    copyObjectMapper.setFilterProvider(new SimpleFilterProvider()
                            .addFilter(FieldFilter.class.getCanonicalName(), new FieldFilter(bodyWrapper)));
                    concurrentHashMap.put(bodyWrapper.cacheKey(), copyObjectMapper);
                    return copyObjectMapper;
                });
    }
}
