package indi.tudan.learn.cloud.cache.service.impl;

import indi.tudan.learn.cloud.cache.config.RedisConfig;
import indi.tudan.learn.cloud.cache.service.RedisService;
import com.al.crm.nosql.cache.CacheFactory;
import com.al.crm.nosql.cache.ICache;
import com.al.crm.nosql.cache.IRedisFix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * 缓存服务
 *
 * @author wangtan
 * @date 2020-05-15 11:35:38
 * @since 1.0
 */
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * ml-cache 配置
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * ml-cache redis 实现
     */
    private ICache redisCache;

    /**
     * ml-cache redis 实现
     */
    private IRedisFix redisFix;

    /**
     * 初始化 ml-cache
     *
     * @author wangtan
     * @date 2020-05-15 14:05:14
     */
    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.setProperty("cache.backend", redisConfig.getCacheBackend());
        props.setProperty("redis.url", redisConfig.getRedisUrl());
        props.setProperty("redis.maxActive", redisConfig.getRedisMaxActive());
        props.setProperty("redis.maxIdle", redisConfig.getRedisMaxIdle());
        props.setProperty("redis.minIdle", redisConfig.getRedisMinIdle());
        redisCache = CacheFactory.getInstance().getCache(props);
    }

    @Override
    public String get(String key) throws Exception {
        return redisCache.get(key, String.class);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) throws Exception {
        return redisCache.get(key, clazz);
    }

    @Override
    public String getDir(String dir, String key) throws Exception {
        return redisCache.get(dir, key, String.class);
    }

    @Override
    public <T> T getDir(String dir, String key, Class<T> clazz) throws Exception {
        return redisCache.get(dir, key, clazz);
    }

    @Override
    public void put(String key, String value) throws Exception {
        redisCache.put(key, value);
    }

    @Override
    public void put(String key, String value, int expire) throws Exception {
        redisCache.put(key, value, expire);
    }

    @Override
    public void put(String key, Object value) throws Exception {
        redisCache.put(key, value);
    }

    @Override
    public void put(String key, Object value, int expire) throws Exception {
        redisCache.put(key, value, expire);
    }

    @Override
    public void putDir(String dir, String key, String value) throws Exception {
        redisCache.put(dir, key, value);
    }

    @Override
    public void putDir(String dir, String key, String value, int expire) throws Exception {
        redisCache.put(dir, key, value, expire);
    }

    @Override
    public void putDir(String dir, String key, Object value) throws Exception {
        redisCache.put(dir, key, value);
    }

    @Override
    public void putDir(String dir, String key, Object value, int expire) throws Exception {
        redisCache.put(dir, key, value, expire);
    }

    @Override
    public boolean putIfNotExists(String key, String value) throws Exception {
        return redisCache.putIfNotExists(key, value);
    }

    @Override
    public boolean putIfNotExists(String key, Object value) throws Exception {
        return redisCache.putIfNotExists(key, value);
    }

    @Override
    public List<String> listKeys(String dir, List<String> excludeSubDirs, String keyWord, int offset, int limit) throws Exception {
        return redisCache.listKeys(dir, excludeSubDirs, keyWord, offset, limit);
    }

    @Override
    public boolean remove(String key) throws Exception {
        return redisCache.remove(key);
    }

    @Override
    public void batchRemove(String dir, List<String> keyList) throws Exception {
        redisCache.batchRemove(dir, keyList);
    }

    @Override
    public void removeAll() throws Exception {
        redisCache.removeAll();
    }

    @Override
    public void expire(String key, int expire) throws Exception {
        redisCache.setExpireTime(key, expire);
    }

    @Override
    public int getExpireTime(String key) throws Exception {
        return redisCache.getExpireTime(key);
    }

    @Override
    public long incr(String key) throws Exception {
        return redisCache.incr(key);
    }

    @Override
    public long incr(String key, long value) throws Exception {
        return redisCache.incr(key, value);
    }

    @Override
    public long decr(String key) throws Exception {
        return redisCache.decr(key);
    }

    @Override
    public long decr(String key, long value) throws Exception {
        return redisCache.decr(key, value);
    }

}
