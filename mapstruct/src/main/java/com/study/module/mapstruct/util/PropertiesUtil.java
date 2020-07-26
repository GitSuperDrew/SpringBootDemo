package com.study.module.mapstruct.util;

import com.study.module.mapstruct.entity.Sku;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 对象拷贝
 *
 * @author Administrator
 * @date 2020/7/26 下午 4:49
 */
public class PropertiesUtil {

    /**
     * 将source对象的属性填充到 destination对应属性中
     *
     * @param source      源对象
     * @param destination 目标对象
     * @param <S>
     * @param <D>
     */
    public static <S, D> void copyProperties(S source, D destination) {
        Class clsDestination;
        try {
            clsDestination = Class.forName(destination.getClass().getName());
            Class clsSource = Class.forName(source.getClass().getName());
            Field[] declaredFields = clsDestination.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                // 跳过serialVersionUID
                try {
                    if ("serialVersionUID".equals(fieldName)) {
                        continue;
                    }
                    Field sourceField = clsSource.getDeclaredField(fieldName);
                    sourceField.setAccessible(true);
                    field.set(destination, sourceField.get(source));
                } catch (NoSuchFieldException e) {
                    continue;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Properties 属性转换错误");
        }
    }

    /**
     * 将源对象的属性，填充到目标对象对应属性中
     *
     * @param source           原始对象
     * @param destination      目标对象
     * @param ignoreProperties 不转换的属性
     * @param <S>
     * @param <D>
     */
    public static <S, D> void copyProperties(S source, D destination, String... ignoreProperties) {
        Class clsDestination;
        try {
            clsDestination = Class.forName(destination.getClass().getName());
            Class clsSource = Class.forName(source.getClass().getName());
            Field[] declaredFields = clsDestination.getDeclaredFields();
            for (Field field : declaredFields) {
                String fieldName = field.getName();
                Set<String> collect = Stream.of(ignoreProperties).collect(Collectors.toSet());
                // 跳过serialVersionUID
                collect.add("serialVersionUID");
                if (collect.contains(fieldName)) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Field sourceField = clsSource.getDeclaredField(fieldName);
                    sourceField.setAccessible(true);
                    field.set(destination, sourceField.get(source));
                } catch (NoSuchFieldException e) {
                    System.out.println("没有对应的属性，跳过");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Properties 属性转换错误");
        }
    }

    /**
     * Spring的BeanUtils.copyProperties() 的List类型
     *
     * @param sourceList
     * @param beanClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> copy(Object sourceList, Class<?> beanClass) throws Exception {
        List<Object> sList = (List<Object>) sourceList;
        List<Object> tList = new ArrayList<>();
        for (Object t : sList) {
            Object dto = beanClass.newInstance();
            BeanUtils.copyProperties(t, dto);
            tList.add(dto);
        }
        return (List<T>) tList;
    }

    public static void main(String[] args) {
        objectCopy();
        copyList();
    }

    /**
     * 测试1：对象拷贝
     */
    public static void objectCopy() {
        Sku sku = new Sku(1L, "A99999", 34);
        Sku copySku = new Sku();
        copyProperties(sku, copySku);
        System.out.println(copySku);
    }

    /**
     * 测试2：List<TestDTO> dtoList = copy(poList, TestDTO.class);
     */
    public static void copyList() {
        Sku sku = new Sku(1L, "A99999", 34);
        List<Sku> intList = Arrays.asList(sku, sku);
        try {
            // 核心部分
            List<Sku> targetList = copy(intList, Sku.class);
            System.out.println(targetList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
