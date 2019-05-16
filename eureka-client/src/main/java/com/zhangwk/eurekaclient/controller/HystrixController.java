package com.zhangwk.eurekaclient.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zhangwk.eurekaclient.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//通过下面的注解可以指定整个controller的回调函数
//具体的方法就不需要指定fallbackMethod
//@DefaultProperties(defaultFallback = "fallback")
public class HystrixController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private HiService hiService;

    //添加Hystrix的注解,并指定回调函数
    @HystrixCommand(commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value="3000")
    },fallbackMethod = "fallback")
    @RequestMapping("/hys/hi")
    public String sayHi(){
        return hiService.hi();
    }

    /**
     * 上面方法指定的回调函数
     * 当上面的方法出现异常或在指定时间未返回时(默认超时时间1s),会调用此函数
     * @return
     */
    public String fallback(){
        return "用户拥挤,请稍后再试!";
    }

    @HystrixCommand(commandProperties = {
            //开启熔断
            @HystrixProperty(name = "circuitBreaker.enabled",value="true"),
            //一个rolling window内最小的请求数。 默认20
            // 如果设为20，那么当一个rolling window(统计时间段,默认10s)的时间内收到19个请求，即使19个请求都失败，也不会触发circuit break(熔断)。
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value="10"),
            //触发短路的时间值，当该值设为5000时，则当触发circuit break后的5000毫秒内都会拒绝request，也就是5000毫秒后才会关闭circuit(断路器)。默认5000
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value="10000"),
            //错误比率阀值，如果错误率>=该值，circuit(断路器)会被打开，并短路所有请求触发fallback。默认50
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value="60")
    },fallbackMethod = "fallback")
    @GetMapping("/hys/circuitSayHi")
    public String circuitSayHi(Integer number){
        if(number == 2){
            return "success";
        }
        return hiService.hi();
    }

    public String fallback(Integer number){
        return "用户拥挤,请稍后再试!";
    }
}
