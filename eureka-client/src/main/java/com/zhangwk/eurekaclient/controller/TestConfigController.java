package com.zhangwk.eurekaclient.controller;

import com.zhangwk.eurekaclient.config.TestCongif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConfigController {
    @Autowired
    TestCongif testCongif;

    @GetMapping("/test/config")
    public String getConfig(){
        return testCongif.toString();
    }
}
