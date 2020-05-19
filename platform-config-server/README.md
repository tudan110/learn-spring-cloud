# platform-config-server
配置中心服务端，可以指定本地仓库。

配置文件命名规则是：{application}-{profile}.properties
配置文件命名规则是：{application}-{profile}.yml

## 如何在不重启微服务的情况下，更新配置信息
 1. 配置中心的文件发生了修改，此时，微服务是不知道的；
 2. 引入 actuator 监控中心完成
 ```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
 ```
 3. 为 ConfigBean 添加 @RefreshScope 注解；

 4. 使用接口测试工具（例如postman）发送post请求actuator/refresh来更新配置内容

    POST 请求监控中心 http://微服务ip:port/actuator/refresh

 5. 在没有引入消息总线的情况下，需要手动请求每个微服务的 actuator/refresh 地址，这样的话太麻烦，可以引入消息总线。使用 rabbitmq 配置成功，由于平台环境不支持，暂时屏蔽，后期有需要考虑使用 kafka 。