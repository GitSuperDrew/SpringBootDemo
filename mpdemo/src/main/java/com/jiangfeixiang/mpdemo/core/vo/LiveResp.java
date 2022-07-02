package com.jiangfeixiang.mpdemo.core.vo;

import lombok.Data;

@Data
public class LiveResp<T> {
    private int code = 200;
    private String message = "";
    private T data;
    /**
     * 响应时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    public LiveResp() {
    }

    public LiveResp(T data) {
        this.data = data;
    }
}
