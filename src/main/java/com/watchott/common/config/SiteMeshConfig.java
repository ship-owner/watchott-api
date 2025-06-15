package com.watchott.common.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : watchott.core.config
 * fileName       : SiteMeshConfig
 * author         : shipowner
 * date           : 2023-09-14
 * description    : decorator 설정
 */

@Configuration
public class SiteMeshConfig extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder
                .addDecoratorPath("/*","/default.jsp")
                .addExcludedPath("/error")
                .addExcludedPath("/comment/*")
                .setMimeTypes("text/html");
    }

    @Bean
    public FilterRegistrationBean<SiteMeshConfig> siteMeshFilter() {
        FilterRegistrationBean<SiteMeshConfig> filter = new FilterRegistrationBean<>();
        filter.setFilter(new SiteMeshConfig());

        return filter;

    }

}