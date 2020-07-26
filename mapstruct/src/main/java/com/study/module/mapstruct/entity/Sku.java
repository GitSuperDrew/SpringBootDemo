package com.study.module.mapstruct.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @date 2020/7/26 上午 11:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sku {
    private Long id;
    private String code;
    private Integer price;
}
