package com.gggitpl.template.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置启用事务管理, 通用分页拦截器
 *
 * @author gggitpl
 */
@Configuration
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDbType(DbType.MYSQL);
        return paginationInterceptor;
    }

    @Bean
    public MyBatisInterceptor myBatisInterceptor() {
        return new MyBatisInterceptor();
    }
}
