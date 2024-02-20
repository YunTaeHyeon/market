package com.study.market.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    public void setStringValue(String token, String data, Long expirationTime){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        stringStringValueOperations.set(token, data, (int)(expirationTime/1), TimeUnit.MILLISECONDS);
    }

}
