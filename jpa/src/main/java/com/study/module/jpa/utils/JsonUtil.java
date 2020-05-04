package com.study.module.jpa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.module.jpa.entity.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * JSON 转换工具
 * <pre>
 *     插件推荐： IDEA 的 JSON 👉 Object插件：GsonFormat
 * </pre>
 *
 * @author Administrator
 * @date 2020年5月4日
 */
public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data * @return
     */
    public static String objectToJson(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     * <p>Title: jsonToList</p>
     * <p>Description: </p>
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = MAPPER.readValue(jsonData, javaType);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 5);
        System.out.println("list to json:\t" + objectToJson(list));

        Map<String, Object> map = Map.of("name", "Drew", "age", 24, "sex", "男");
        System.out.println("map to json:\t" + objectToJson(map));

        List<UserEntity> userList = Arrays.asList(
                new UserEntity(1, "Drew", "123123"),
                new UserEntity(2, "King", "321321"),
                new UserEntity(3, "Baby", "5201314"),
                new UserEntity(4, "Mary", "5211314")
        );
        String json = objectToJson(userList);
        System.out.println("List<UserEntity> → JSONObject:\n" + json);
        System.out.println("JSONObject → List<UserEntity>:\n" + jsonToList(json, UserEntity.class));
    }
}