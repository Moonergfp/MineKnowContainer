package com.springoauth2.demo.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by wuhuachuan on 16/5/9.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.springoauth2.demo.*" })
@EntityScan(basePackages = { "com.springoauth2.demo.*" })
@EnableJpaRepositories(basePackages = { "com.springoauth2.demo.*" })
public class Boot {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Boot.class, args);
    }
}
