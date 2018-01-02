package com.lock.test.config;

import redis.clients.jedis.Jedis;

/**
 * Created by wuhuachuan on 2017/2/9.
 */
public class RedisConfig {

    private static final String IP = "localhost";
    private static final int PORT = 6379;

    private static Jedis jedis = new Jedis(IP,PORT);

    private RedisConfig(){}

    public static Jedis getInstance(){
        return jedis;
    }
}
