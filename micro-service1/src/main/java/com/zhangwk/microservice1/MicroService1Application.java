package com.zhangwk.microservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroService1Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroService1Application.class, args);
    }

}
