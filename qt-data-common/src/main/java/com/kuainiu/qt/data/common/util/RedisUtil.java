package com.kuainiu.qt.data.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: ckhero
 * Date: 2019/7/20
 * Time: 11:17 AM
 */
@Slf4j
public final class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    DefaultRedisScript<Boolean> setIfAbsentScript;

    @Autowired
    DefaultRedisScript<Long> getAndSubtractScript;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    public Object get(String key) {
        return key == null ? null: redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setNx(String key, String value, long expire ) {
        boolean res = false;
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            res =  stringRedisTemplate.execute(setIfAbsentScript, keys, value, String.valueOf(expire));

        } catch (Exception e) {
            log.error("Redis lock fail,key={},value={}", key, value);
            log.error("Redis lock fail,e", e);
        }
        return res;

    }

    public boolean setNx(String key, Long value, long expire ) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);

    }

    public Integer getAndSubtract(String key, Integer qty, long expire ) {
        Integer res = 0;
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            Long resaa =  stringRedisTemplate.execute(getAndSubtractScript, keys, String.valueOf(qty), String.valueOf(expire));
            log.error("key,{}", key);
            log.error("getAndSubtract,{}e", resaa);
            log.error("getAndSubtract,{}e", resaa.intValue());
            res = resaa.intValue();
        } catch (Exception e) {
            log.error("getAndSubtract,e", e);
        }
        return res;
    }

    public long decrement(String key, Integer value, long expire){
        long res = redisTemplate.opsForValue().decrement(key, value);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return res;
    }

    public long increment(String key, Integer value, long expire){
        long res = redisTemplate.opsForValue().increment(key, value);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return res;
    }

    public BigDecimal increment(String key, BigDecimal value, long expire){
        double res = redisTemplate.opsForValue().increment(key, value.doubleValue());
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        return new BigDecimal(res);
    }
}
