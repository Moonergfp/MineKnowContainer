package com.springoauth.code.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by wuhuachuan on 16/5/9.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.springoauth.code.*" })
@EntityScan(basePackages = { "com.springoauth.code.*" })
@EnableJpaRepositories(basePackages = { "com.springoauth.code.*" })
public class Boot {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Boot.class, args);
    }
}
