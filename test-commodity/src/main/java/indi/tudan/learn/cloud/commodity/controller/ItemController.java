package indi.tudan.learn.cloud.commodity.controller;

import indi.tudan.learn.cloud.commodity.config.CommonConfigBean;
import indi.tudan.learn.cloud.commodity.config.JdbcConfigBean;
import indi.tudan.learn.cloud.commodity.entity.Item;
import indi.tudan.learn.cloud.commodity.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private ItemService itemService;

    @Autowired
    private JdbcConfigBean jdbcConfigBean;

    @Autowired
    private CommonConfigBean commonConfigBean;

    /**
     * 对外提供接口服务，查询商品信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "item/{id}")
    public Item queryItemById(@PathVariable("id") Long id) {
        System.out.println("service port：" + port);
        return this.itemService.queryItemById(id);
    }

    @GetMapping(value = "testConfig")
    public String testConfig() {
        return this.jdbcConfigBean.toString();
    }

    @GetMapping(value = "commonConfig")
    public String commonConfig() {
        return this.commonConfigBean.toString();
    }

}
