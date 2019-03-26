package com.potato369.novel.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.manage
 * @InterfaceName RedisManager
 * @Desc Redis缓存管理接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/02 15:52
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface RedisManager {
    /**
     * 查询字符串
     *
     * @param key
     * @return
     */
    public String getStr(String key);

    /**
     * 查询字符串（截取start-end）
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    String getSubStr(String key, long start, long end);

    /**
     * 保存字符串
     *
     * @param key
     * @param value
     */
    void setStr(String key, String value);

    /**
     * 保存字符串（带失效时间，单位秒）
     *
     * @param key
     * @param value
     * @param timeout
     */
    void setStrAndExpire(String key, String value, long timeout);

    /**
     * 覆盖保存字符串（从偏移量offset开始）
     *
     * @param key
     * @param value
     * @param offset
     */
    void setStrAndCover(String key, String value, long offset);

    /**
     * 保存字符串(已存在返回false)
     *
     * @param key
     * @param value
     * @return
     */
    boolean setStrIfAbsent(String key, String value);

    /**
     * 批量保存字符串
     *
     * @param params
     */
    void multiSetStr(Map<String, String> params);

    /**
     * 批量保存字符串(已存在返回false)
     *
     * @param params
     */
    boolean multiSetStrIfAbsent(Map<String, String> params);

    /**
     * 查询失效时间（单位秒）
     *
     * @param key
     * @return
     */
    long getExpire(String key);

    /**
     * 设置失效时间（单位秒）
     *
     * @param key
     * @param timeout
     * @return
     */
    boolean setExpire(String key, long timeout);

    /**
     * 检查key是否存在
     *
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * 删除键值对
     *
     * @param key
     */
    void delete(String key);

    /**
     * 批量删除键值对
     *
     * @param keys
     */
    void multiDelete(List<Object> keys);

    /**
     * 重命名key
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    boolean renameKeyIfAbsent(String oldKey, String newKey);

    /**
     * 检查hashKey是否存在
     *
     * @param key
     * @param hashKey
     * @return
     */
    boolean hasHashKey(String key, Object hashKey);

    /**
     * 查询hashKey值
     *
     * @param key
     * @param hashKey
     * @return
     */
    Object hashGet(String key, Object hashKey);

    /**
     * 查询哈希表
     *
     * @param key
     * @return
     */
    Map<String, Object> getHashMap(String key);

    /**
     * 删除hashKey值
     *
     * @param key
     * @param hashKeys
     * @return
     */
    long hashDelete(String key, Object... hashKeys);

    /**
     * 保存hashKey值
     *
     * @param key
     * @param hashKey
     * @param hashValue
     */
    void hashPut(String key, Object hashKey, Object hashValue);

    /**
     * 保存hashKey值(已存在返回false)
     *
     * @param key
     * @param hashKey
     * @param hashValue
     */
    boolean hashPutIfAbsent(String key, Object hashKey, Object hashValue);

    /**
     * 保存哈希表
     *
     * @param key
     * @param map
     */
    void setHashMap(String key, HashMap<String, Object> map);

    /**
     * 查询list集合
     *
     * @param key
     * @return
     */
    List<Object> getList(String key);

    /**
     * 保存list值至列表头
     *
     * @param key
     * @param values
     */
    void leftListPush(String key, String... values);

    /**
     * 保存list至列表头
     *
     * @param key
     * @param value
     */
    void leftSetList(String key, List<Object> value);

    /**
     * 保存list值至列表尾
     *
     * @param key
     * @param values
     */
    void rightListPush(String key, String... values);

    /**
     * 保存list至列表尾
     *
     * @param key
     * @param value
     */
    void rightSetList(String key, List<Object> value);

    /**
     * 弹出列表头元素
     *
     * @param key
     * @return
     */
    String leftListPop(String key);

    /**
     * 弹出列表尾元素
     *
     * @param key
     * @return
     */
    String rightListPop(String key);

    /**
     * 删除list中值
     * count> 0：删除等于从头到尾移动count个等于value的元素
     * count <0：删除等于从尾到头移动count个等于value的元素
     * count = 0：删除等于value的所有元素
     *
     * @param key
     * @param count
     * @param value
     */
    void ListRemove(String key, long count, Object value);
}
