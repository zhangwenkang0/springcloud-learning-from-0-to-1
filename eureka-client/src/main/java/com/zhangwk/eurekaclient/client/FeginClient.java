package com.zhangwk.eurekaclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("service-hi")
public interface  FeginClient {
    @GetMapping("hi")
    String sayHi();
}
