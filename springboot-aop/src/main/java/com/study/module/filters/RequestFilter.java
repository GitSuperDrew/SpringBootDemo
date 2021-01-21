package com.study.module.filters;

import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 请求参数过滤（请求进入顺序：过滤器 > servlet > Interceptor >  controller中的AOP）
 *
 * @author zl
 * @date 2021/1/20 15:23
 **/
public class RequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String ip = httpServletRequest.getRemoteAddr();
        String url = httpServletRequest.getRequestURI();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        System.out.println("请求参数 < Method-One：\t" + getRequestParams((HttpServletRequest) servletRequest));
        System.out.printf(">>>>   %s 访问了 %s%n", dateStr, ip, url);
        System.out.println("请求参数 < Method-Two：\t" + getReqParams((HttpServletRequest) servletRequest));

        filterChain.doFilter(httpServletRequest, httpServletResponse);
        System.out.println("方法耗时：" + (System.currentTimeMillis() - startTime));

    }

    @Override
    public void destroy() {

    }


    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!ObjectUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    /**
     * 获取请求参数(方法一)
     *
     * @param request 请求URL
     * @return
     */
    private static Map<String, Object> getRequestParams(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(5);
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            result.put(paraName, request.getParameter(paraName));
        }
        return result;
    }

    /**
     * 获取请求参数（方法二）
     *
     * @param request 请求URL
     * @return 参数Map
     */
    private static Map<String, Object> getReqParams(HttpServletRequest request) {
        HashMap result = new HashMap(16);
        Map map = request.getParameterMap();
        Set keSet = map.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if (ov instanceof String[]) {
                value = (String[]) ov;
            } else {
                value[0] = ov.toString();
            }

            for (int k = 0; k < value.length; k++) {
                // System.out.println(ok + "=" + value[k]);
                result.put(ok, value[k]);
            }
        }
        return result;
    }


}
