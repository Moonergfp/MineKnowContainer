package com.lock.test.main;

import com.lock.test.config.RedisConfig;
import com.lock.test.utils.MysqlUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * Created by wuhuachuan on 2017/2/9.
 */
public class App1Test {
    private static final String LOCK_KEY = "APP_LOCK_KEY";
    private static final String LOCK_VALUE = "APP1_LOCK_VALUE";
    private static final int LOCK_TIME = 5; // 5 seconds

    public static void main(String args[]) throws IOException {
        getLock();
        doSomething();
        releaseLock();
    }

    public static void getLock() {
        while (true){
            Jedis jedis = RedisConfig.getInstance();
            if(jedis.setnx(LOCK_KEY,LOCK_VALUE) == 1){
                //设置过期时间
                jedis.expire(LOCK_KEY,LOCK_TIME);
                break;
            }
        }
        System.out.println("app1 get lock");
    }

    private static void doSomething() {
        int numbers = MysqlUtil.executeSql("select numbers from goods");
        System.out.println("db goods numbers = " + numbers);

        if(numbers >= 9){
            int result = MysqlUtil.executeSql("update goods set numbers = " + (numbers - 9) + ";");
            if(result == 1){
                System.out.println("app1:buy success");
            } else {
                System.out.println("app1:buy fail");
            }
        }

        //模拟执行时间过长，或者宕机导致 redis 锁释放。
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void releaseLock() throws IOException {
        Jedis jedis = RedisConfig.getInstance();

        if(LOCK_VALUE.equals(jedis.get(LOCK_KEY))){
            jedis.del(LOCK_KEY);
        } else {
            //业务回滚
            getLock();
            rollback();
            releaseLock();
        }
    }

    private static void rollback() {
        int numbers = MysqlUtil.executeSql("select numbers from goods");
        int result = MysqlUtil.executeSql("update goods set numbers = " + (numbers + 9) + ";");
        if(result == 1){
            System.out.println("app1 rollback success");
        } else {
            System.out.println("app1 rollback fail");
        }
    }
}
