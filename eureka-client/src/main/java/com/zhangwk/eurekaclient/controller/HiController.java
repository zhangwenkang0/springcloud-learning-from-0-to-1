package com.zhangwk.eurekaclient.controller;

import com.zhangwk.eurekaclient.client.FeginClient;
import com.zhangwk.eurekaclient.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    private HiService hiService;
    @Autowired
    private FeginClient feginClient;

    @GetMapping("hi")
    public String hi() {
        return hiService.hi();
    }

    @GetMapping("hiFromFegin")
    public String hiFromFegin() {
        return feginClient.sayHi();
    }
}
