package com.cx.qt.data.common.config;

import com.cx.qt.data.common.util.RedisLock;
import com.cx.qt.data.common.util.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/7/20
 * Time: 10:48 AM
 */
@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    RedisUtil redisUtil() {
        return new RedisUtil();
    }

    @Bean
    RedisLock redisLock() {
        return new RedisLock();
    }
}
