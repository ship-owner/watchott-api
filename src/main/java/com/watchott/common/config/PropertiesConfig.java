package com.watchott.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


/**
 * packageName    : watchott.config
 * fileName       : PropertiesConfig
 * author         : shipowner
 * date           : 2023-09-11
 * description    : 외부 환경 변수 불러오는 설정
 */
@Configuration
public class PropertiesConfig {

    @Value("${movie.config.location}")
    private String configLocation;

    @Bean(name = "movieProp")
    public PropertiesFactoryBean movieProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new FileSystemResource(configLocation));
        return propertiesFactoryBean;
    }

}
