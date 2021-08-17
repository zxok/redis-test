package com.example.redis.service.impl;

import com.example.redis.service.IUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Program redis-test
 * @Author HeZhenXiao
 * @Since JDK 1.8
 * @Date 2021-08-16 14:04
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Override
    @Cacheable(value = "redis-test#600", key = "#id", condition = "#id != null")
    public String test(String id) {
        return id;
    }
}
