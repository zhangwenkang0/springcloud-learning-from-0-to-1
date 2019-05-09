package com.zhangwk.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class TokenFilter extends ZuulFilter {

    /**
     * 过滤器类型
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     *  优先级，数字越大，优先级越低
     *  只做一般过滤,没有特殊要求的,可以放在内置过滤器后面
     * @return
     */
    @Override
    public int filterOrder() {
        //最后一个内置PRE过滤器后面
        return PRE_DECORATION_FILTER_ORDER+1;
    }

    /**
     * 是否执行该过滤器，true代表需要过滤
     * 这里可以忽略掉一些不需要过滤请求
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        //如果没有token,则返回401
        //这里就不做校验了,只判断有无
        if(StringUtils.isEmpty(token)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            ctx.setResponseBody("未登陆!");
            //设置返回体的编码为UTF-8
            HttpServletResponse response = ctx.getResponse();
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
        }
        return null;
    }
}
