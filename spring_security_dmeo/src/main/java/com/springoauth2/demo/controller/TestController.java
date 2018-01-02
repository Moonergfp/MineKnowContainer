package com.springoauth2.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wuhuachuan on 16/5/11.
 */

@RestController
public class TestController {

    @RequestMapping(value = "/test")
    public void test(){
        System.out.println("test!");
    }
}
