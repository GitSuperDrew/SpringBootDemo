package com.study.module.springbootmybatis.tips;

import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * List集合如何去重复？有以下四种方法，可以试试！
 * 学习地址：https://mp.weixin.qq.com/s/NnGQi_9HsKhYDWN6Jrh-ow
 *
 * @author Administrator
 * @date 2020/11/13 下午 9:11
 */
public class DistinctList {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(10, 12, 12, 3, 10, 0, 5, 6);
        List<String> stringList = Lists.newArrayList("d", "a", "c", "a", "d");

        System.out.println("第 1 种方式 >>> (位置有序)");
        System.out.println(forEachDistinctList(list));
        System.out.println(forEachDistinctList(stringList));

        System.out.println("第 2 种方式 >>> （无序）");
        System.out.println(hashSetMethod(list));
        System.out.println(hashSetMethod(stringList));

        System.out.println("第 3 种方式 >>> （小到大）");
        System.out.println(treeSetMethod(list));
        System.out.println(treeSetMethod(stringList));

        System.out.println("第 4 种方式 >>> （有序）");
        System.out.println(removeDuplicationByTreeSet(list));
        System.out.println(removeDuplicationByTreeSet(stringList));

        System.out.println("第 5 种方式 >>> (位置有序)");
        List<Integer> newIntlist = list.stream().distinct().collect(Collectors.toList());
        List<String> newStrlist = stringList.stream().distinct().collect(Collectors.toList());
        System.out.println(newIntlist);
        System.out.println(newStrlist);

    }

    public static List<Object> forEachDistinctList(List<?> list) {
        List<Object> result = Lists.newArrayList();
        for (Object o : list) {
            if (result.isEmpty()) {
                result.add(o);
            } else if (!result.contains(o)) {
                result.add(o);
            }
        }
        return result;
    }

    public static List<Object> hashSetMethod(List<?> list) {
        List<Object> result = Lists.newArrayList();
        HashSet<Object> hashSet = new HashSet<>(list);
        result.addAll(hashSet);
        return result;
    }

    public static List<Object> treeSetMethod(List<?> list) {
        List<Object> result = Lists.newArrayList();
        TreeSet<Object> treeSet = new TreeSet<>(list);
        // 将所有的元素填充到新的集合里面去
        result.addAll(treeSet);
        return result;
    }


    /**
     * 使用TreeSet实现List去重(有序)
     *
     * @param list
     */
    public static List removeDuplicationByTreeSet(List<?> list) {
        TreeSet set = new TreeSet(list);
        //把List集合所有元素清空
        list.clear();
        //把HashSet对象添加至List集合
        list.addAll(set);
        return list;
    }

}
