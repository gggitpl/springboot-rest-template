package com.gggitpl.template.controller.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gggitpl.template.model.BaseEntity;
import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * @author gggitpl
 */
@Slf4j
@FieldNameConstants
@Data
public class BaseQuery<E extends BaseEntity> {

    private String sort;

    private Long size = 15L;

    private Long current = 1L;

    private Long total = 0L;

    public Page<E> page() {
        return new Page<>(current, size, total);
    }

    public QueryWrapper<E> queryWrapper() {
        QueryWrapper<E> queryWrapper = Wrappers.query();
        /*构建查询条件*/
        Arrays.stream(this.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        String name = field.getName();
                        Object value = field.get(this);
                        if (nonNull(value)
                                && !"log".equals(name)
                                && !Fields.sort.equals(name)
                                && !Fields.size.equals(name)
                                && !Fields.current.equals(name)
                                && !Fields.total.equals(name)) {
                            if (value instanceof String) {
                                String val = String.valueOf(value);
                                /*构建like语句*/
                                if (endsWith(val, ":L")) {
                                    queryWrapper.likeLeft(column(name), removeEnd(val, ":L"));
                                } else if (endsWith(val, ":R")) {
                                    queryWrapper.likeRight(column(name), removeEnd(val, ":R"));
                                } else if (endsWith(val, ":LR")) {
                                    queryWrapper.like(column(name), removeEnd(val, ":LR"));
                                } else {
                                    queryWrapper.eq(column(name), value);
                                }
                            } else {
                                queryWrapper.eq(column(name), value);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        log.error("通过反射构建查询条件时出错: {}", e.getMessage(), e);
                    }
                });

        if (StringUtils.isNotBlank(sort)) {
            Splitter.on(",")
                    .trimResults()
                    .splitToList(sort)
                    .forEach(s -> {
                        if (startsWith(s, "+")) {
                            queryWrapper.orderByAsc(column(removeStart(s, "+")));
                        } else if (startsWith(s, "-")) {
                            queryWrapper.orderByDesc(column(removeStart(s, "-")));
                        }
                    });
        }
        return queryWrapper;
    }

    private String column(String attr) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, attr);
    }

}
