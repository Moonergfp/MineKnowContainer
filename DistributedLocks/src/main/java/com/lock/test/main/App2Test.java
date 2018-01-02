package com.lock.test.main;

import com.lock.test.config.RedisConfig;
import com.lock.test.utils.MysqlUtil;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * Created by wuhuachuan on 2017/2/9.
 */
public class App2Test {
    private static final String LOCK_KEY = "APP_LOCK_KEY";
    private static final String LOCK_VALUE = "APP2_LOCK_VALUE";
    private static final int LOCK_TIME = 5; // 5 seconds

    public static void main(String args[]) throws IOException {
        getLock();
        doSomething();
        releaseLock();
    }

    public static void getLock() {

        //模拟先让 App1 拿到锁
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true){
            Jedis jedis = RedisConfig.getInstance();
            if(jedis.setnx(LOCK_KEY,LOCK_VALUE) == 1){
                //设置过期时间
                jedis.expire(LOCK_KEY,LOCK_TIME);
                break;
            }
        }

        System.out.println("app2 get lock");
    }

    private static boolean doSomething() throws IOException {
        int numbers = MysqlUtil.executeSql("select numbers from goods");
        System.out.println("db goods numbers = " + numbers);

        if(numbers >= 2){
            int result = MysqlUtil.executeSql("update goods set numbers = " + (numbers - 2) + ";");
            if(result == 1){
                System.out.println("app2:buy success");
                return true;
            } else {
                System.out.println("app2:buy fail");
                return false;
            }
        } else {
            //重试，因为 app1 可能会回滚，这样 goods 的数量又够了
            for(int i = 0 ; i < 3; ++i){
                //每次重试隔2秒
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("app2 retrying...");

                getLock();
                boolean flag = doSomething();
                releaseLock();
                if(flag == true){
                    return true;
                }
            }
            return false;
        }
    }

    private static void releaseLock() throws IOException {
        Jedis jedis = RedisConfig.getInstance();

        if(LOCK_VALUE.equals(jedis.get(LOCK_KEY))){
            jedis.del(LOCK_KEY);
        } else {
            //业务回滚
//            getLock();
//            rollback();
//            releaseLock();
        }
    }

    private static void rollback() {
        int numbers = MysqlUtil.executeSql("select numbers from goods");
        int result = MysqlUtil.executeSql("update goods set numbers = " + (numbers + 2) + ";");
        if(result == 1){
            System.out.println("app1 rollback success");
        } else {
            System.out.println("app1 rollback fail");
        }
    }
}
