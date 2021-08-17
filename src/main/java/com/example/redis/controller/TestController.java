package com.example.redis.controller;

import com.example.redis.service.IUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @Program redis-test
 * @Author HeZhenXiao
 * @Since JDK 1.8
 * @Date 2021-08-16 13:46
 **/
@RestController("test")
public class TestController {

    @Resource
    IUserService userService;

    @GetMapping("get")
    public String get(String id) {
        return "test-get-".concat(userService.test(id));
    }
}
