package com.study.event.api.config;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// global cross origin setting
public class CrossOriginConfig implements WebMvcConfigurer {

    private String[] urls = {
            "http://localhost:3000",
            "http://localhost:3001",
            "http://localhost:3002",
            "http://localhost:3003",
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/auth/**") //어떤 url 요청에서
                .allowedOrigins(urls) // 어떤 클라이언트를
                .allowedMethods("*")  // 어떤 방식에서
                .allowedHeaders("*") // 어떤 헤더를 허용할 지
                .allowCredentials(true) // 쿠키 전송을 허용할 지
        ;
    }
}
