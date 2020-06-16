package com.gggitpl.template.mybatis;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将mybatis查询语句中的驼峰转下划线
 *
 * @author gggitpl
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MyBatisInterceptor implements Interceptor {

    private static final Pattern JAVA_PROPERTY_PATTERN = Pattern.compile("[a-z]+([A-Z][a-z]*)+");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /*拦截sql语句, 驼峰转下划线*/
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        Matcher matcher = JAVA_PROPERTY_PATTERN.matcher(sql);
        while (matcher.find()) {
            String val1 = matcher.group();
            sql = sql.replace(val1, CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, val1));
        }
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, sql);
        return invocation.proceed();
    }
}