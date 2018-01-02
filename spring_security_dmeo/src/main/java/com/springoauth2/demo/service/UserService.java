package com.springoauth2.demo.service;

import com.springoauth2.demo.entity.User;

/**
 * Created by wuhuachuan on 16/5/11.
 */
public interface UserService {

    public User findByUsername(String username);
}
