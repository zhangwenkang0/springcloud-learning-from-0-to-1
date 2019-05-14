package com.zhangwk.eurekaclient.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhangwk.eurekaclient.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HystrixController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private HiService hiService;

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/hys/hi")
    public String sayHi(){
        return hiService.hi();
    }

    public String fallback(){
        return "用户拥挤,请稍后再试!";
    }
}
