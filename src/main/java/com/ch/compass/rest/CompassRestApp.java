package com.ch.compass.rest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@MapperScan("com.ch.compass.core.persistence.mapper")
public class CompassRestApp {

    public static void main(String[] args) {
        SpringApplication.run(CompassRestApp.class, args);
    }
}
