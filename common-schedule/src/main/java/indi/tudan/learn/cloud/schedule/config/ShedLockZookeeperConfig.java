package indi.tudan.learn.cloud.schedule.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.zookeeper.curator.ZookeeperCuratorLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SchedulerLock 基于 Redis 的配置
 *
 * <pre>
 *     @ EnableSchedulerLock(defaultLockAtMostFor = "PT55S")
 *
 *     defaultLockAtMostFor 指定在执行节点结束时应保留锁的默认时间使用ISO8601 Duration格式
 *     作用就是在被加锁的节点挂了时，无法释放锁，造成其他节点无法进行下一任务
 *     这里默认55s
 *     关于ISO8601 Duration格式用的不到，具体可上网查询下相关资料，应该就是一套规范，规定一些时间表达方式
 * </pre>
 *
 * @author wangtan
 * @date 2020-05-18 16:36:43
 * @since 1.0
 */
@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT55S")
public class ShedLockZookeeperConfig {

    @Bean
    @SuppressWarnings("all")
    public LockProvider scheduledLockConfiguration(CuratorFramework client) {
        return new ZookeeperCuratorLockProvider(client);
    }
}
