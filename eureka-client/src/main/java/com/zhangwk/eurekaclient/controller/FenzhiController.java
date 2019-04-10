package com.zhangwk.eurekaclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class FenzhiController {
/*    @Value("${fenzhi}")
    private String fenzhi;

    @GetMapping("fenzhi")
    public String getFenzhi(){mqTestDefault
        return fenzhi;
    }*/
}
