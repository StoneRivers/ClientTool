package com.jymf.dfs.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Json与Object互工具类
 * @author Zhang
 * @version 0.1
 */
public class JsonTool {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    /**
     * Object转Json
     * @param data Object 待转对象
     * @return String 转化后的Json字符串
     */
    public static String toJson (Object data){
        return gson.toJson(data);
    }

    /**
     * Json转Object
     * @param json 待转Json字符串
     * @param clazz 转化结果对象类的Class类型
     * @param <T> 转化结果对象的类型
     * @return T 转化结果
     * @throws JsonSyntaxException Json数据和对象格式不统一,无法进行转化
     */
    public static<T> T toObject (String json, Class<T> clazz) throws JsonSyntaxException{
        return gson.fromJson(json,clazz);
    }
}
