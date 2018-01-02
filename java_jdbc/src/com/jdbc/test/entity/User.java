package com.jdbc.test.entity;

/**
 * Created by wuhuachuan on 16/5/17.
 */

public class User {
    private String username;
    private String password;

    public User(){}
    public User(String username,String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
