package com.icbc.match;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableApolloConfig
@DubboComponentScan
@EnableDubboConfig(multiple = true)
public class YkgyjApplication {
    public static void main(String[] args) {
        SpringApplication.run(YkgyjApplication.class, args);
    }
}
