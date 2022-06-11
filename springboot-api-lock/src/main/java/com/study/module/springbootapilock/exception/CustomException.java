package com.study.module.springbootapilock.exception;

import lombok.*;

/**
 * @author zl
 * @create 2022-06-11 10:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {

    private String status;
    private String message;

}

