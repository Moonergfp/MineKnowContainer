package com.jdbc.test.main;

import com.jdbc.test.entity.User;
import java.sql.*;

public class Main {

    private final static String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private final static String DEFAULT_MYSQL_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private final static String DEFAULT_MYSQL_USERNAME = "root";
    private final static String DEFAULT_MYSQL_PASSWORD = "123456";

    private final static String DEFAULT_USER_SELECT_STATEMENT = "select * from tb_user";

    public static void main(String[] args) throws SQLException {
        //1. 加载驱动类
        try{
            Class.forName(MYSQL_DRIVER_NAME) ;
        }catch(ClassNotFoundException e){
            e.printStackTrace() ;
        }

        //2. 提供JDBC连接的URL,USERNAME,PASSWORD


        //3. 创建数据库的连接
        Connection connection = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection(DEFAULT_MYSQL_URL,DEFAULT_MYSQL_USERNAME,DEFAULT_MYSQL_PASSWORD);

            //4. 创建 Statement对象
            Statement statement = connection.createStatement();

            //5. 执行SQL 语句
            resultSet = statement.executeQuery(DEFAULT_USER_SELECT_STATEMENT);

            //6. 处理结果
            while(resultSet.next() == true){
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                User user = new User(username,password);
                System.out.println("user = " + user);
            }

            // 7. 关闭链接
            resultSet.close();
            connection.close();

        } finally {
            if(resultSet != null ){
                resultSet.close();
            }
            if(connection != null ){
                connection.close();
            }
        }
    }
}
