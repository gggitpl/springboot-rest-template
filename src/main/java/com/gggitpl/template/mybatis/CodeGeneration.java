package com.gggitpl.template.mybatis;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.gggitpl.template.controller.BaseController;
import com.gggitpl.template.model.BaseEntity;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;


/**
 * mybatis-plus 根据数据库表生成实体类/mapper/service/controller<br/>
 * <pre>
 * public static void main(String[] args) {
 *         CodeGeneration.builder()
 *                 .author("gggitpl")
 *                 .host("112.126.60.61")
 *                 .port("15245")
 *                 .db("account_service_db")
 *                 .username("root")
 *                 .password("Vk9xxzuw")
 *                 .parent("com.hidongtop.account")
 *                 .build()
 *                 .execute();
 *     }
 * </pre>
 */
@SuppressWarnings("ALL")
@Data
@Builder
public class CodeGeneration {
    private String author;
    private String host;
    private String port;
    private String db;
    private String username;
    private String password;
    private String parent;

    public String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public GlobalConfig globalConfig() {
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setServiceName("%sService");
        gc.setFileOverride(false);
        gc.setOpen(false);
        gc.setSwagger2(false);
        return gc;
    }

    public DataSourceConfig dataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + host + ":" + port + "/" + db + "?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(username);
        dsc.setPassword(password);
        return dsc;
    }

    public PackageConfig packageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setParent(parent);
        pc.setEntity("model");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        pc.setXml("mapper");
        return pc;
    }

    public StrategyConfig strategyConfig(PackageConfig pc) {
        StrategyConfig strategy = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("t_")
                .setSuperEntityClass(BaseEntity.class)
                .setSuperEntityColumns("id", "create_time", "update_time", "deleted")
                .setEntityLombokModel(true)
                .setRestControllerStyle(true)
                .setSuperControllerClass(BaseController.class)
                .setInclude(scanner("表名，多个英文逗号分割").split(","))
                .setControllerMappingHyphenStyle(true);
        return strategy;
    }

    public InjectionConfig injectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        return cfg;
    }

    public TemplateConfig templateConfig() {
        TemplateConfig tc = new TemplateConfig();
        //tc.setXml(null);
        return tc;
    }

    public void execute() {
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(globalConfig());
        mpg.setDataSource(dataSourceConfig());
        mpg.setPackageInfo(packageConfig());
        mpg.setStrategy(strategyConfig(packageConfig()));
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.setCfg(injectionConfig());
        mpg.setTemplate(templateConfig());
        mpg.execute();
    }
}
