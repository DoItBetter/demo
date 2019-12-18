package com.cx.qt.demo.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.cx.qt.demo")
@MapperScan("com.cx.qt.demo.dal.dao")
@EnableAsync
public class QtDemoWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(QtDemoWebApplication.class, args);
    }
}
