package com.ectocyst.allproject.comfig;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Damon . yanli
 * @Description:
 * @Date: Created in 14:02 2020-11-11
 * @Modified By:
 */
@Component
@Configuration
public class FilterConfig{

    @Bean
    public FilterRegistrationBean filter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        EncryptFilter encryptFilter = new EncryptFilter();
        registration.setFilter(encryptFilter);
        registration.addUrlPatterns("/*");
        registration.setName("encryptFilter");
        registration.setOrder(1);
        return registration;
    }


}