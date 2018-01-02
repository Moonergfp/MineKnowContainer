package com.rabbitmq.test.withspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by wuhuachuan on 17/1/12.
 */
@EnableAutoConfiguration
@ComponentScan(basePackages="com.rabbitmq.test")
public class Boot {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Boot.class, args);
    }
}
