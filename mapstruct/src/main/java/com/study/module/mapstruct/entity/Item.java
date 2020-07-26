package com.study.module.mapstruct.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @date 2020/7/26 上午 11:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private Long id;
    private String title;
}
