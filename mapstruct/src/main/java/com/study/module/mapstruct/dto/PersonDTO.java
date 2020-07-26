package com.study.module.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 * @date 2020/7/26 上午 11:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 出生日期（对DO里面的字段进行扩展，dateFormat形式）
     */
    private String birthDateFormat;
    /**
     * 出生日期（对DO里面的字段进行扩展，expression形式）
     */
    private String birthExpressionFormat;
}
