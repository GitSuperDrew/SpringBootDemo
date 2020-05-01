package com.study.module.jpa.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {
    private Integer currentPage;
    private Integer pageCount;
    private String orderColumn;
    private String isDesc;
}
