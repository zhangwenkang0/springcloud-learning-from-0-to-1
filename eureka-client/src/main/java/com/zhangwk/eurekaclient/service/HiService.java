package com.zhangwk.eurekaclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HiService {
    @Autowired
    private RestTemplate restTemplate;

    public String hi() {
        return restTemplate.getForObject("http://SERVICE-HI/hi",String.class);
    }
}
