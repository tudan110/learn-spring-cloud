package indi.tudan.learn.cloud.cache;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;


/**
 * 缓存服务
 *
 * @author wangtan
 * @date 2020-05-15 11:22:08
 * @since 1.0
 */
@EnableSwagger2Doc
@EnableEurekaClient
@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
public class CacheServerApplication {

    public static void main(String... args) {
        SpringApplication.run(CacheServerApplication.class, args);
    }

    /**
     * 配置 Swagger
     */
    @Component
    @Primary
    static class DocumentationConfig implements SwaggerResourcesProvider {

        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> resource = new ArrayList<>();

            // name 可以随便写，location 前缀要与 zuul 配置的 path 一致
            resource.add(swaggerResource("cache-service", "/v2/api-docs?token=1", "2.0"));
            return resource;
        }

        /**
         * name 可以随便写，location 前缀要与 zuul 配置的 path 一致
         *
         * @param name     接口服务名
         * @param location 接口服务地址
         * @param version  版本
         * @return 接口资源
         */
        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }

    }

}
