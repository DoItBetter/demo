package com.kuainiu.qt.data.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.kuainiu.qt.data")
@MapperScan("com.kuainiu.qt.data.dal.dao")
@EnableAsync
public class QtDataWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(QtDataWebApplication.class, args);
    }
}
