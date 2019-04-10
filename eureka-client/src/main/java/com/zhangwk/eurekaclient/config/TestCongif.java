package com.zhangwk.eurekaclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("test")
public class TestCongif {
    private String name;
    private Integer age;
    private String sex;
}
