package indi.tudan.learn.cloud.cache.service;

import java.util.List;

/**
 * 缓存服务接口
 *
 * @author wangtan
 * @date 2020-05-15 11:34:52
 * @since 1.0
 */
public interface RedisService {

    String get(String key) throws Exception;

    <T> T get(String key, Class<T> clazz) throws Exception;

    String getDir(String dir, String key) throws Exception;

    <T> T getDir(String dir, String key, Class<T> clazz) throws Exception;

    void put(String key, String value) throws Exception;

    void put(String key, String value, int expire) throws Exception;

    void put(String key, Object value) throws Exception;

    void put(String key, Object value, int expire) throws Exception;

    void putDir(String dir, String key, String value) throws Exception;

    void putDir(String dir, String key, String value, int expire) throws Exception;

    void putDir(String dir, String key, Object value) throws Exception;

    void putDir(String dir, String key, Object value, int expire) throws Exception;

    boolean putIfNotExists(String key, String value) throws Exception;

    boolean putIfNotExists(String key, Object value) throws Exception;

    List<String> listKeys(String dir, List<String> excludeSubDirs, String keyWord,
                          int offset, int limit) throws Exception;

    boolean remove(String key) throws Exception;

    void batchRemove(String dir, List<String> keyList) throws Exception;

    void removeAll() throws Exception;

    void expire(String key, int expire) throws Exception;

    int getExpireTime(String key) throws Exception;

    long incr(String key) throws Exception;

    long incr(String key, long value) throws Exception;

    long decr(String key) throws Exception;

    long decr(String key, long value) throws Exception;

}
