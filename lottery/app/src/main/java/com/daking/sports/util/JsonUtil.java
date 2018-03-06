package com.daking.sports.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

/**
 * Created by 18 on 2017/9/9.
 * 描述：json工具类  json字符串与对象之间相互转换
 */

public class JsonUtil {

    private JsonUtil() {
    }

    /**
     * json转对象
     *
     * @param json  json字符串
     * @param clazz 目标对象的class类型
     * @return 目标对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

    /**
     * json转对象
     *
     * @param json json字符串
     * @param type 目标对象的class类型
     * @return 目标对象
     */
    public static <T> T fromJson(JsonElement json, Type type) {
        return new Gson().fromJson(json, type);
    }

    /**
     * 对象转json
     *
     * @param object 待转为json的对象
     * @return json字符串
     */
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }
}
