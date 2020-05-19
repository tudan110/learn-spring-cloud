/**
 *
 */
package indi.tudan.learn.cloud.cache;

import com.al.crm.nosql.cache.CacheFactory;
import com.al.crm.nosql.cache.ICache;

import java.util.Properties;

public class MlCacheTest {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("redis.maxActive", "100");
        props.setProperty("redis.maxIdle", "30");
        props.setProperty("redis.minIdle", "10");
        props.setProperty("cache.backend", "redis");
        props.setProperty("redis.url", "redis://:asiainfo2020@10.19.83.89:6379/0");
        ICache cache = CacheFactory.getInstance().getCache(props);
        cache.putIfNotExists("mlcacheKey", "mlcacheValue");
        System.out.println(cache.get("mlcacheKey"));
        cache.destroy();
    }

}
