package com.kuainiu.qt.data.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/9/4
 * Time: 3:21 PM
 */
@Slf4j
public class RedisLock {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    DefaultRedisScript<Boolean> releaseScript;

    @Autowired
    RedisUtil redisUtil;

    //加锁间隔时间50毫秒
    private final long INTERVAL_TIME = 50;

    private final long EXPIRE_TIME = 10000;

    private final String CHARSET_UTF8 = "UTF-8";

    //加锁超时时间
    private Integer TIMEOUT = 5000;

    private static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    public boolean tryLock(String key, String requestId) {
        long startTime = System.currentTimeMillis();
        long passTime = 0l;
        boolean res;
        while (passTime <= TIMEOUT) {

            passTime = System.currentTimeMillis() - startTime;
            res = redisUtil.setNx(key, requestId, EXPIRE_TIME);
            if (res) {
                return res;
            }
            try {
                Thread.sleep(INTERVAL_TIME);
            } catch (InterruptedException e) {
                return false;
            }
        }
        log.error("Redis lock fail,key={}",key);
        return false;
    }

    public boolean releaseLock(String key, String requestId) {
        boolean res = false;
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            res =  stringRedisTemplate.execute(releaseScript, keys, requestId);

        } catch (Exception e) {
            log.error("Redis lock fail,key={},value={}", key, requestId);
            log.error("Redis lock fail,e", e);
        }
        return res;
    }


    public String getRequestId() {
        return String.valueOf(System.currentTimeMillis());
    }
}
