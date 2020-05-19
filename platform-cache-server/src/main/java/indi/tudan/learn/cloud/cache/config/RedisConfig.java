package indi.tudan.learn.cloud.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class RedisConfig {

    @Value("${ml-cache.cache.backend}")
    private String cacheBackend;

    @Value("${ml-cache.redis.url}")
    private String redisUrl;

    @Value("${ml-cache.redis.maxActive}")
    private String redisMaxActive;

    @Value("${ml-cache.redis.maxIdle}")
    private String redisMaxIdle;

    @Value("${ml-cache.redis.minIdle}")
    private String redisMinIdle;

    public String getCacheBackend() {
        return cacheBackend;
    }

    public void setCacheBackend(String cacheBackend) {
        this.cacheBackend = cacheBackend;
    }

    public String getRedisUrl() {
        return redisUrl;
    }

    public void setRedisUrl(String redisUrl) {
        this.redisUrl = redisUrl;
    }

    public String getRedisMaxActive() {
        return redisMaxActive;
    }

    public void setRedisMaxActive(String redisMaxActive) {
        this.redisMaxActive = redisMaxActive;
    }

    public String getRedisMaxIdle() {
        return redisMaxIdle;
    }

    public void setRedisMaxIdle(String redisMaxIdle) {
        this.redisMaxIdle = redisMaxIdle;
    }

    public String getRedisMinIdle() {
        return redisMinIdle;
    }

    public void setRedisMinIdle(String redisMinIdle) {
        this.redisMinIdle = redisMinIdle;
    }

    @Override
    public String toString() {
        return "ResClassTree [cache.backend=" + cacheBackend
                + ", redis.url=" + redisUrl
                + ", redis.maxActive=" + redisMaxActive
                + ", redis.maxIdle=" + redisMaxIdle
                + ", redis.minIdle=" + redisMinIdle
                + "]";
    }
}
