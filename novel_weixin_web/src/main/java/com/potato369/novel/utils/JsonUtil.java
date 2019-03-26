package com.potato369.novel.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Objects;

/**
 * <pre>
 * @PackageName com.potato369.novel.utils
 * @ClassName JsonUtil
 * @Desc JsonUtil
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/03/14 11:11
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    /**
     * <pre>
     * Convert  target object to json string.
     * @param o target object.
     * @return converted json string.
     * </pre>
     */
    public static String toJson(Object o) {
        gsonBuilder.setPrettyPrinting();
        return gsonBuilder.create().toJson(o);
    }
    /**
     * <pre>
     * Convert json string to target object.
     *
     * @param json  json string.
     * @param valueType target object class type.
     * @param <T>  target class type.
     * @return converted target object.
     * </pre>
     */
    public static <T> T toObject(String json, Class<T> valueType) {
        Objects.requireNonNull(json, "json is null.");
        Objects.requireNonNull(valueType, "value type is null.");
        try {
            return mapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new IllegalStateException("fail to convert [" + json + "] to [" + valueType + "].", e);
        }
    }

}
