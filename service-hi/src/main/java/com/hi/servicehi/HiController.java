package com.hi.servicehi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Value("${server.port}")
    public String port;

    @GetMapping("hi")
    public String hi() {
        return "hi!i am come from " + port;
    }
}
