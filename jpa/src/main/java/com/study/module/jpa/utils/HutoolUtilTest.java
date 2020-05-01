package com.study.module.jpa.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.Setting;
import cn.hutool.setting.SettingUtil;
import com.google.common.collect.ImmutableMap;
import org.w3c.dom.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class HutoolUtilTest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Map<String, Object> map = ImmutableMap.of("one", list, "two", list, "three", list);
//        System.out.println(URLUtil.encode("http://www.google.com.cn/word=你好"));
//        System.out.println("自动为url拼接http://以及去除重复的/" + URLUtil.normalize("www.baidu.com"));
//        System.out.println(StrUtil.format("{}爱{}，就像老鼠爱大米。", "我", "你"));
//        System.out.println("现在时间:" + DateTime.now());
//        System.out.println("敏感数据加密：" + SecureUtil.md5("12343435"));
//        System.out.println("hutool的JSONUtil使用：" + JSONUtil.toJsonStr(map));
    }
}
