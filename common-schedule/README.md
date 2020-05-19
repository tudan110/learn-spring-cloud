# common-schedule
分布式任务调度实现，通过基于 zookeeper 的 shedlock 分布式锁来实现。

## 使用方法

### 1. 引入 common-schedule 模块或 jar 包。

```xml
<dependency>
    <groupId>indi.tudan</groupId>
    <artifactId>common-schedule</artifactId>
</dependency>
```

### 2. 在原本的定时任务方法上增加注解 @SchedulerLock
以 spring schedule 为例，代码如下：

```java
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 分布式定时任务示例，redis 锁
 *
 * @author wangtan
 * @date 2020-04-17 16:30:58
 * @since 1.0
 */
@Slf4j
@Component
public class CronTask {

    /*
     * @SchedulerLock注解一共支持五个参数，分别是
     *
     * name：用来标注一个定时服务的名字，被用于写入数据库作为区分不同服务的标识，如果有多个同名定时任务则同一时间点只有一个执行成功
     * lockAtMostFor：成功执行任务的节点所能拥有独占锁的最长时间，单位是毫秒ms
     * lockAtMostForString：成功执行任务的节点所能拥有的独占锁的最长时间的字符串表达，例如“PT14M”表示为14分钟，单位可以是S,M,H
     * lockAtLeastFor：成功执行任务的节点所能拥有独占所的最短时间，单位是毫秒ms
     * lockAtLeastForString：成功执行任务的节点所能拥有的独占锁的最短时间的字符串表达，例如“PT14M”表示为14分钟,单位可以是S,M,H
     *
     */

    // 区分服务
    @Value("${spring.application.name}")
    private String appName;

    @Scheduled(cron = "*/1 * * * * ?") // 每 5s
    //@Scheduled(cron="0 */1 * * * ?") // 每 1 min
    @SchedulerLock(name = "test_lock", lockAtMostFor = 3 * 60 * 1000, lockAtLeastFor = 5 * 1000)
    public void testMethod() {

        //do something
        log.info("{}: 执行定时任务 test-lock:testMethod。", appName);

    }

}
```