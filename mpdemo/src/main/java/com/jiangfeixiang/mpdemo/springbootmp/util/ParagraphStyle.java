package com.jiangfeixiang.mpdemo.springbootmp.util;

import lombok.Data;

/**
 * 段落样式
 *
 * @author Administrator
 * @date 2020/8/10 下午 8:55
 */
@Data
public class ParagraphStyle {
    private boolean isSpace;  //是否缩进
    private String before;      //段前磅数
    private String after;  //段后磅数
    private String beforeLines;        //段前行数
    private String afterLines;        //段后行数
    private boolean isLine;        //是否间距
    private String line;    //间距距离
    //段落缩进信息
    private String firstLine;
    private String firstLineChar;
    private String hanging;
    private String hangingChar;
    private String right;
    private String rightChar;
    private String left;
    private String leftChar;


}
