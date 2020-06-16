package com.gggitpl.template;

import com.gggitpl.template.filter.FieldFiltering;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SpringbootRestTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestTemplateApplication.class, args);
    }

    @FieldFiltering(aClass = User.class, fields = User.Fields.password)
    @FieldFiltering(aClass = Info.class, fields = {Info.Fields.sex, Info.Fields.age})
    @GetMapping
    public User hello() {
        final Info info = new Info(1L, 18, "男", "西四环北路114号附近");
        return new User(1L, "你二大爷", "ietrhdwqjitdutr", info);
    }

    @Data
    @FieldNameConstants
    static class User {
        private final Long id;
        private final String username;
        private final String password;
        private final Info info;
    }

    @Data
    @FieldNameConstants
    static class Info {
        private final Long id;
        private final Integer age;
        private final String sex;
        private final String address;
    }
}
