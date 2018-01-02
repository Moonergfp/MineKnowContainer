package com.springoauth2.demo.serviceImpl;

import com.springoauth2.demo.dao.UserDao;
import com.springoauth2.demo.entity.User;
import com.springoauth2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuhuachuan on 16/5/11.
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
