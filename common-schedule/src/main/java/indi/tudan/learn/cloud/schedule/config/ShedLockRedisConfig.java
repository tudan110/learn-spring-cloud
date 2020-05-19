/*
package com.ai.gaimops.schedule.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

*/
/**
 * SchedulerLock 基于 Redis 的配置
 *
 * @author wangtan
 * @date 2020-04-17 16:17:46
 * @since 1.0
 *//*

@Configuration
@EnableScheduling
// defaultLockAtMostFor 指定在执行节点结束时应保留锁的默认时间使用ISO8601 Duration格式
// 作用就是在被加锁的节点挂了时，无法释放锁，造成其他节点无法进行下一任务
// 这里默认55s
// 关于ISO8601 Duration格式用的不到，具体可上网查询下相关资料，应该就是一套规范，规定一些时间表达方式
@EnableSchedulerLock(defaultLockAtMostFor = "PT55S")
public class ShedLockRedisConfig {

    @Bean
    @SuppressWarnings("all")
    public LockProvider scheduledLockConfiguration(RedisConnectionFactory factory) {
        return new RedisLockProvider(factory);
    }
}
*/
