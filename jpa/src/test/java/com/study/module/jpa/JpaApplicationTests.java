package com.study.module.jpa;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import org.checkerframework.framework.qual.CFComment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class JpaApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * 学习网址
     * ① https://hutool.cn/docs/#/ （及时更新方法的调整）
     * ② https://how2j.cn/k/hutool/
     */
    @Test
    void hutoolTest(){
        // 学习 Hutool 地址：https://how2j.cn/k/hutool/hutool-date/1946.html

        System.out.println("Hutool工具类的使用：");
        // DateUtil - hutool (https://how2j.cn/k/hutool/hutool-date/1946.html)
        System.out.println("当前时间为：" + DateUtil.now());

        // ClassUtil - hutool (https://how2j.cn/k/hutool/hutool-class/1959.html)
        System.out.println("获取Map类中的public方法：\n" + ClassUtil.getPublicMethodNames(Map.class));

        // StrUtil - hutool (https://how2j.cn/k/hutool/hutool-str/1948.html)
        // 1.移除所有的空格
        System.out.println(StrUtil.removeAll("dong dong dong", " "));
        // 2.指定字符串中找出目标字符出现的次数(区分大小写)
        System.out.println(StrUtil.count("absgasdegfsadfewesfsaEFDSDG", 'a'));

        // NumberUtil - hutool (https://how2j.cn/k/hutool/hutool-number/1963.html)
        System.out.println(NumberUtil.range(1, 10));

        // UnicodeUtil - hutool (https://how2j.cn/k/hutool/hutool-unicode/1975.html)
        System.out.println(UnicodeUtil.toUnicode("Hello Hutool!"));

        // URLUtil
        System.out.println(URLUtil.decode("www.baidu.com"));
    }

}
