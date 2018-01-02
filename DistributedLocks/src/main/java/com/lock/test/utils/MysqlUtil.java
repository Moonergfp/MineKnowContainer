package com.lock.test.utils;

import com.lock.test.config.MysqlConfig;

import java.sql.*;

/**
 * Created by wuhuachuan on 2017/2/15.
 */
public class MysqlUtil {
    public static int executeSql(final String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName(MysqlConfig.DRIVER_CLASS);
            connection = DriverManager.getConnection(MysqlConfig.URL, MysqlConfig.USER_NAME, MysqlConfig.PASSWORD);
            statement = connection.createStatement();

            if(sql.startsWith("select")){
                resultSet = statement.executeQuery(sql);
                resultSet.next();
                return resultSet.getInt(1);
            } else {
                return statement.executeUpdate(sql);
            }

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

        return -1;
    }
}
