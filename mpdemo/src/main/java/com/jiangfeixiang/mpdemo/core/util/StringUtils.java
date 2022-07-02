package com.jiangfeixiang.mpdemo.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParseException;

import java.io.IOException;
import java.util.*;

/**
 * 自定义字符串工具类，集成 common-lang3
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 判断对象为空
     *
     * @param obj 对象
     * @return boolean 返回类型(不空的时候返回true，其他返回false)
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null || obj.toString().length() == 0) {
            //System.out.println("obj");
            return true;
        } else if (obj instanceof String) {
            //System.out.println("String");
            return ((String) obj).isEmpty() || ((String) obj).length() == 0;
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0 || ((Map) obj).isEmpty();
        } else if (obj instanceof HashMap) {
            return ((HashMap) obj).size() == 0 || ((HashMap) obj).isEmpty();
        } else if (obj instanceof Vector) {
            return ((Vector) obj).size() == 0 || ((Vector) obj).isEmpty();
        } else if ((obj instanceof List)) {
            //System.out.println("List");
            return ((List) obj).size() == 0 || ((List) obj).isEmpty();
        } else if ((obj instanceof ArrayList)) {
            // System.out.println("ArrayList");
            return ((ArrayList) obj).size() == 0 || ((ArrayList) obj).isEmpty();
        } /*else if (obj instanceof R) {
            R r = (R) obj;
            Object data = r.getData();
            return isEmpty(data);
        }*/
        return false;
    }

    /**
     * 判断对象不能为空
     *
     * @param obj 对象集合
     * @return boolean 返回类型(不为空的时候返回true，其他返回false)
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断数组对象不能为空
     *
     * @param objs 对象集合
     * @return boolean 返回类型(不为空的时候返回true，其他返回false)
     */
    public static boolean isNotEmpty(Object[] objs) {
        boolean reflag = true;
        if (objs != null && objs.length > 0) {
            for (Object obj : objs) {
                boolean flag = isNotEmpty(obj);
                if (flag) {
                    break;
                }
            }
        } else {
            reflag = false;
        }
        return reflag;
    }

    /**
     * 判断数组对象不能为空
     *
     * @param objs 对象集合
     * @return boolean 返回类型(不为空的时候返回true，其他返回false)
     */
    public static boolean isNotEmpty(List<Object> objs) {
        boolean reflag = true;
        if (objs != null && objs.size() > 0) {
            for (Object obj : objs) {
                boolean flag = isNotEmpty(obj);
                if (flag) {
                    break;
                }
            }
        } else {
            reflag = false;
        }
        return reflag;
    }

    /**
     * 计算字符串内某个字符出现的次数
     */
    public static int searchCount(String str, String searchStr) {
        if (isEmpty(str) || isEmpty(searchStr)) {
            return 0;
        }

        int found = 0;
        int index = -1;
        while ((index = str.indexOf(searchStr, index)) > -1) {
            index += 1;
            found++;
        }
        return found;
    }


    /**
     * 获取字符串长度（中文算2个)
     *
     * @param str 字符串
     * @return 字符串长度
     */
    public static long getWordLength(String str) {
        if (str == null || "".equals(str)) {
            return 0;
        }

        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            int ascii = Character.codePointAt(str, i);
            if (ascii >= 0 && ascii <= 255) {
                length++;
            } else {
                length += 2;
            }
        }
        return length;
    }


    /**
     * json string 转换成map
     *
     * @param jsonString map json string
     * @return map对象
     * @throws Exception 异常
     */
    public static Map<String, Object> jsonStringToMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = mapper.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {
            });
            Set<String> set = map.keySet();
            for (String key : set) {
                Object o = map.get(key);
                o = (o == null ? "" : o.toString());
                map.put(key, o.toString().trim());
            }
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 检查是否重复的数据
     *
     * @param array 数组
     * @return boolean false表示有重复,true表示没重复的数据
     */
    public static boolean checkRepeat(Object[] array) {
        HashSet<Object> hashSet = new HashSet<>();
        Collections.addAll(hashSet, array);
        // true:没重复的值 false: 表示有重复的值
        return hashSet.size() == array.length;
    }

    /**
     * 去掉重复的值
     *
     * @param list 原始集合
     * @return List 返回类型
     */
    public static List removeRepeatValue(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 拼接in的参数1, 2
     */
    public static String sqlInStr(List<String> params) {
        StringBuilder values = new StringBuilder();
        for (String param : params) {
            values.append(param).append(",");
        }
        return values.substring(0, values.length() - 1);
    }
}
