package com.example.redis.config;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Map;

/**
 * @Description 重写RedisCacheManager，以制定每个缓存的有效时间
 * @Program redis-test
 * @Author HeZhenXiao
 * @Since JDK 1.8
 * @Date 2021-08-16 14:14
 **/
public class CustomRedisCacheManager extends RedisCacheManager {

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
    }

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
    }

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
    }

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        Duration ttl = getTtlByName(name);
        if (ttl != null) {
            //证明在cacheName上使用了过期时间，需要修改配置中的ttl
            cacheConfig = cacheConfig.entryTtl(ttl);
        }
        //修改缓存key和value值的序列化方式
        return super.createRedisCache(name, cacheConfig);
    }

    /**
     * 缓存参数的分隔符
     * 数组元素0=缓存的名称
     * 数组元素1=缓存过期时间TTL
     */
    private static final String DEFAULT_PATH = "#";

    /**
     * 通过name获取过期时间
     * @param name
     * @return
     */
    private Duration getTtlByName(String name) {
        if (name == null) {
            return null;
        }
        //根据分隔符拆分字符串，并进行过期时间ttl的解析
        String[] cacheParams = name.split(DEFAULT_PATH);
        if (cacheParams.length > 1) {
            String ttl = cacheParams[1];
            if (!StringUtils.isEmpty(ttl)) {
                try {
                    return Duration.ofSeconds(Long.parseLong(ttl));
                } catch (Exception e) {
                }
            }
        }
        return Duration.ofSeconds(30);
    }
}

