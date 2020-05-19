package indi.tudan.learn.cloud.eureka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaController {

    @GetMapping("eureka")
    public String getEureka() {
        return "这是Eureka注册中心！！";
    }

}
