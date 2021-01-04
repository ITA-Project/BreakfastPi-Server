package com.ita.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Dillon Xie
 * @date 12/29/2020
 */
@Component
public class RedisConfig {
    @Bean
    public StringRedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }
}