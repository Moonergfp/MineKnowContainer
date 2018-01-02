package com.lock.test.main;

import com.lock.test.config.MysqlConfig;

import java.sql.*;

/**
 * Created by wuhuachuan on 2017/2/15.
 */
public class CrashTest {

    public static void main(String args[]){
        executeSql();
    }

    public static void executeSql() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MysqlConfig.DRIVER_CLASS);
            connection = DriverManager.getConnection(MysqlConfig.URL, MysqlConfig.USER_NAME, MysqlConfig.PASSWORD);
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.executeUpdate("update goods set numbers = 3");

            connection.commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null) {
                    statement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
