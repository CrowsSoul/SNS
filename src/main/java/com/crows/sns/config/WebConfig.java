package com.crows.sns.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径
                .allowedOrigins("http://localhost:8080") // 允许的前端服务器地址
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的方法
                .allowedHeaders("*") // 允许的头信息
                .allowCredentials(true); // 允许发送Cookie
    }
}

