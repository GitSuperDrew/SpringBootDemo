package com.study.module.mybatis.util;

import com.google.common.collect.Lists;
import com.study.module.mybatis.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类
 *
 * @author Administrator
 * @date 2020/9/12 下午 10:49
 */
public class TestDemo {

    public static void main(String[] args) throws Exception {
//        testCollectionUtils();
        setProp();
    }

    public static void testCollectionUtils() {
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = Lists.newArrayList(1, 2, 3);
        List<Integer> list3 = Lists.newArrayList(2, 3, 4, 5);
        System.out.println(CollectionUtils.isEmpty(list1));
        // 交集
        System.out.println(CollectionUtils.retainAll(list2, list3));
        // 并集
        System.out.println(CollectionUtils.union(list2, list3));
        // 差集
        System.out.println(CollectionUtils.subtract(list2, list3));
        // 判等
        System.out.println(CollectionUtils.isEqualCollection(list2, list3));
    }

    public static void setProp() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        User user = new User();
        BeanUtils.setProperty(user, "password", "Drew");
        System.out.println(BeanUtils.getProperty(user, "password"));
    }
}
