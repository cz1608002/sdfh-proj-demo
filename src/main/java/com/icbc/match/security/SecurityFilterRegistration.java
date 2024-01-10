package com.icbc.match.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class SecurityFilterRegistration {

    @Bean
    public FilterRegistrationBean securityFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(securityFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("SecurityFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public Filter securityFilter() {
        return new SecurityFilter();
    }
}
