package com.example.eurekazuulclient.config;

import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Administrator
 * @date 2020/5/20 上午 9:39
 */
public interface ZuulFallbackProvider {

    String getRoute();

    ClientHttpResponse fallbackResponse();
}
