package com.famsun.apicodegenerator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {

    // ========== 基础配置（按你的项目修改） ==========
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gp_demo?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root@123456";
    private static final String TABLE_NAME = "base_app_info"; // 要生成的表名
    private static final String MODULE_NAME = "api-proxy-app"; // 业务模块名
    private static final String BASE_PACKAGE = "com.famsun.momapi.app"; // 业务模块根包
    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "/" + MODULE_NAME + "/src/main/java";
    private static final String AUTHOR = "Gwangdana"; // 作者

    public static void main(String[] args) {
        FastAutoGenerator.create(DB_URL, DB_USERNAME, DB_PASSWORD)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author(AUTHOR)
                            .disableOpenDir()
                            .outputDir(OUTPUT_DIR);
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent(BASE_PACKAGE)
                            .entity("entity")
                            .mapper("mapper")
                            .service("service")
                            .controller("controller")
                            .xml("mapper/xml");
                    builder.pathInfo(Collections.singletonMap(
                            OutputFile.xml,
                            System.getProperty("user.dir") + "/" + MODULE_NAME + "/src/main/resources/mapper/xml"
                    ));
                })
                // 策略配置（适配 MP3.5+ 新 API）
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAME)
                            // 1. Entity 配置
                            .entityBuilder()
                            .superClass("com.famsun.momapi.core.entity.BaseEntity")
                            .disableSerialVersionUID()
                            .enableLombok()
                            .addSuperEntityColumns("id", "add_time", "update_time", "add_user_id", "add_user_name", "update_user_id", "update_user_name")
                            // 2. Mapper 配置
                            .mapperBuilder()
                            .superClass("com.famsun.momapi.core.mapper.BaseMapper")
                            .enableMapperAnnotation()
                            // 3. Service 配置（关键修正：适配新版 API，无 interface 实现类）
                            .serviceBuilder()
                            .superServiceClass("com.famsun.momapi.core.service.BaseService")
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sService") // 让接口和实现类同名，只生成一个文件
                            // 4. Controller 配置（可选）
                            .controllerBuilder()
                            .enableRestStyle();
                })
                // 模板配置（使用自定义 service 模板）
                .templateConfig(builder -> {
                    builder.service("/templates/service.java.ftl") // 指向自定义模板
                            .serviceImpl(null); // 不生成 impl 文件
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
