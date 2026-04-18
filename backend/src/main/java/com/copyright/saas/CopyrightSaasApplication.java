package com.copyright.saas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * AI 版权侵权舆情管控 SaaS 系统启动类
 */
@SpringBootApplication
@MapperScan("com.copyright.saas.mapper")
@EnableScheduling
public class CopyrightSaasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CopyrightSaasApplication.class, args);
        System.out.println("=====================================");
        System.out.println("AI Copyright SaaS System Started!");
        System.out.println("=====================================");
    }
}
