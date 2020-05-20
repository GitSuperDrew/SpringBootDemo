package com.example.eurekazuulclient.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Zuul 中使用自定义过滤器
 *
 * @author Administrator
 * @date 2020/5/20 上午 10:16
 */
@Component
public class MyFilter extends ZuulFilter {
    /**
     * 记录日志属性
     */
    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public String filterType() {
        // 过滤器的四种类型：pre/post/routing/error
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 过滤器的顺序，值越小，越早执行该过滤器。
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 表示该过滤器是否过滤逻辑，如果为true，则执行 run() 方法；如果为false，则不执行 run() 方法。
        return true;
    }

    /**
     * 具体的过滤逻辑：过滤掉未携带 token 的请求
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getParameter("token");
        if (accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        log.info("ok");
        return null;
    }
}
