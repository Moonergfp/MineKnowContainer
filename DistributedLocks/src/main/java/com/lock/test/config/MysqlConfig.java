package com.lock.test.config;

/**
 * Created by wuhuachuan on 2017/2/14.
 */
public class MysqlConfig {
    public static final String IP = "localhost";
    public static final int PORT = 3306;
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "";
    public static final String DATABASE = "test";

    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://"+ IP +":"+ PORT + "/"+ DATABASE;
}
