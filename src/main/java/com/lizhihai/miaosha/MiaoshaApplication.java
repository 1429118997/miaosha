package com.lizhihai.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MiaoshaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }
}
