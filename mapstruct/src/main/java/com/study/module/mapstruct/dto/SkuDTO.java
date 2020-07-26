package com.study.module.mapstruct.dto;

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
public class SkuDTO {
    private Long skuId;
    private String skuCode;
    private Integer skuPrice;
    private Long itemId;
    private String itemName;
}
