package com.study.module.springbootmongodb.utils;

import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * @date 2020/11/14 下午 10:45
 */
public class MongoDbUtils {
    private static final String[] FBS_ARR = {"\\", "$", "(", ")", "*", ".", "+", "[", "]", "?", "^", "{", "}", "|"};

    /**
     * regex对输入特殊字符转义
     *
     * @param keyWord 输入字符
     * @return 转义后的输入字符
     */
    public static String escapeExprSpecialWord(String keyWord) {
        if (!StringUtils.isEmpty(keyWord)) {
            for (String key : FBS_ARR) {
                if (keyWord.contains(key)) {
                    keyWord = keyWord.replace(key, "\\" + key);
                }
            }
        }
        return keyWord;
    }
}
