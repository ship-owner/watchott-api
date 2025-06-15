package com.watchott.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName    : com.watchott.common.config
 * fileName       : WebConfig
 * author         : shipowner
 * date           : 2025-05-31
 * description    :
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 API 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:5173", "http://127.0.0.1:5173") // React 개발 서버의 Origin 명시
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true) // 쿠키와 같은 자격 증명(인증 정보) 허용 (필요한 경우)
                .maxAge(3600); // CORS Pre-flight 요청 결과를 캐싱할 시간 (초 단위)
    }
}
