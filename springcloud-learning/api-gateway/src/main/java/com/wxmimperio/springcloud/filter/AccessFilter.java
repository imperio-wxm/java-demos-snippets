package com.wxmimperio.springcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        // 请求被路由之前执行
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 过滤器的执行顺序, 当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 判断该过滤器是否需要被执行
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());

        // 验证accessToken 参数是否为空，为空则不进行转发路由
        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            log.warn("access token is empty");
            // 过滤该请求，不对其进行路由
            ctx.setSendZuulResponse(false);
            // 设置错误码
            ctx.setResponseStatusCode(401);
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
