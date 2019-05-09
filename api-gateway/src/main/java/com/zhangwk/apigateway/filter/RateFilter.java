package com.zhangwk.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

@Component
public class RateFilter extends ZuulFilter {
    /**
     * 创建令牌桶 容量为1(为了方便测试,这里设置成1)
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //设置顺序 请求转发过滤器 前
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取不到令牌
        if(!RATE_LIMITER.tryAcquire()){
            //TODO 跳转到错误页面或友好提示
            throw new RuntimeException("访问人数过多,请稍后再试!");
        }
        return null;
    }
}
