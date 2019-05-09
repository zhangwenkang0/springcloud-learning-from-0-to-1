package com.zhangwk.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        //是否支持Cookie跨域
        config.setAllowCredentials(true);
        //支持的原始域 *表示所有
        config.setAllowedOrigins(Arrays.asList("*"));
        //允许的头 *表示所有
        config.setAllowedHeaders(Arrays.asList("*"));
        //允许的请求方法 POST,GET等 *表示所有
        config.setAllowedMethods(Arrays.asList("*"));
        //缓存时间 表示300秒内对相同请求不需要再判断
        config.setMaxAge(300L);

        //path参数为对哪些域名进行设置 /**表示所有
        source.registerCorsConfiguration("/**",config);
        return  new CorsFilter(source);
    }
}
