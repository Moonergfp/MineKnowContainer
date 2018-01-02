package com.springoauth2.demo.dao;

import com.springoauth2.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wuhuachuan on 16/5/11.
 */
public interface UserDao extends JpaRepository<User,String>{

    @Query("select user from User user where username = :username and enable = true")
    User findByUsername(@Param("username") String username);
}
