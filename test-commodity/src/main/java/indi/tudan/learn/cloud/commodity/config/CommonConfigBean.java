package indi.tudan.learn.cloud.commodity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class CommonConfigBean {

    @Value("${test.config.name}")
    private String name;

    @Override
    public String toString() {
        return "JdbcConfigBean [name=" + name + "]";
    }
}
