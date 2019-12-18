package com.cx.qt.data.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 2019/10/17
 * Time: 11:07 AM
 */
@Configuration
public class LuaConfig {
    @Bean
    public DefaultRedisScript<Boolean> releaseScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/release.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean
    public DefaultRedisScript<Boolean> setIfAbsentScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/set-if-absent.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean
    public DefaultRedisScript<Long> getAndSubtractScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/get-and-subtract.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}

