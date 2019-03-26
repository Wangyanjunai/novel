package com.potato369.novel.manage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.potato369.novel.manage.RedisManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * @PackageName com.potato369.novel.manage.impl
 * @ClassName RedisManagerImpl
 * @Desc Redis管理接口实现类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/02 15:54
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service(value = "redisManager")
public class RedisManagerImpl implements RedisManager{

    @Autowired
    private StringRedisTemplate redisTemp;

    /**
     * 查询字符串
     *
     * @param key
     * @return
     */
    @Override
    public String getStr(String key) {
        return redisTemp.opsForValue().get(key);
    }

    /**
     * 查询字符串（截取start-end）
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public String getSubStr(String key, long start, long end) {
        return redisTemp.opsForValue().get(key, start, end);
    }

    /**
     * 保存字符串
     *
     * @param key
     * @param value
     */
    @Override
    public void setStr(String key, String value) {
        redisTemp.opsForValue().set(key, value);
    }

    /**
     * 保存字符串（带失效时间，单位秒）
     *
     * @param key
     * @param value
     * @param timeout
     */
    @Override
    public void setStrAndExpire(String key, String value, long timeout) {
        redisTemp.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 覆盖保存字符串（从偏移量offset开始）
     *
     * @param key
     * @param value
     * @param offset
     */
    @Override
    public void setStrAndCover(String key, String value, long offset) {
        redisTemp.opsForValue().set(key, value, offset);
    }

    /**
     * 保存字符串(已存在返回false)
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean setStrIfAbsent(String key, String value) {
        return redisTemp.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 批量保存字符串
     *
     * @param params
     */
    @Override
    public void multiSetStr(Map<String, String> params) {
        redisTemp.opsForValue().multiSet(params);
    }

    /**
     * 批量保存字符串(已存在返回false)
     *
     * @param params
     */
    @Override
    public boolean multiSetStrIfAbsent(Map<String, String> params) {
        return redisTemp.opsForValue().multiSetIfAbsent(params);
    }

    /**
     * 查询失效时间（单位秒）
     *
     * @param key
     * @return
     */
    @Override
    public long getExpire(String key) {
        return redisTemp.getExpire(key);
    }

    /**
     * 设置失效时间（单位秒）
     *
     * @param key
     * @param timeout
     * @return
     */
    @Override
    public boolean setExpire(String key, long timeout) {
        return redisTemp.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 检查key是否存在
     *
     * @param key
     * @return
     */
    @Override
    public boolean hasKey(String key) {
        return redisTemp.hasKey(key);
    }

    /**
     * 删除键值对
     *
     * @param key
     */
    @Override
    public void delete(String key) {
        redisTemp.delete(key);
    }

    /**
     * 批量删除键值对
     *
     * @param keys
     */
    @Override
    public void multiDelete(List keys) {
        redisTemp.delete(keys);
    }

    /**
     * 重命名key
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    @Override
    public boolean renameKeyIfAbsent(String oldKey, String newKey) {
        return redisTemp.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 检查hashKey是否存在
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public boolean hasHashKey(String key, Object hashKey) {
        return redisTemp.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 查询hashKey值
     *
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public Object hashGet(String key, Object hashKey) {
        return redisTemp.opsForHash().get(key, hashKey);
    }

    /**
     * 查询哈希表
     *
     * @param key
     * @return
     */
    @Override
    public Map getHashMap(String key) {
        return redisTemp.opsForHash().entries(key);
    }

    /**
     * 删除hashKey值
     *
     * @param key
     * @param hashKeys
     * @return
     */
    @Override
    public long hashDelete(String key, Object... hashKeys) {
        return redisTemp.opsForHash().delete(key, hashKeys);
    }

    /**
     * 保存hashKey值
     *
     * @param key
     * @param hashKey
     * @param hashValue
     */
    @Override
    public void hashPut(String key, Object hashKey, Object hashValue) {
        redisTemp.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 保存hashKey值(已存在返回false)
     *
     * @param key
     * @param hashKey
     * @param hashValue
     */
    @Override
    public boolean hashPutIfAbsent(String key, Object hashKey, Object hashValue) {
        return redisTemp.opsForHash().putIfAbsent(key, hashKey, hashValue);
    }

    /**
     * 保存哈希表
     *
     * @param key
     * @param map
     */
    @Override
    public void setHashMap(String key, HashMap<String, Object> map) {
        redisTemp.opsForHash().putAll(key, map);
    }

    /**
     * 查询list集合
     *
     * @param key
     * @return
     */
    @Override
    public List getList(String key) {
        return redisTemp.opsForList().range(key, 0, -1);
    }

    /**
     * 保存list值至列表头
     *
     * @param key
     * @param values
     */
    @Override
    public void leftListPush(String key, String... values) {
        redisTemp.opsForList().leftPushAll(key, values);
    }

    /**
     * 保存list至列表头
     *
     * @param key
     * @param value
     */
    @Override
    public void leftSetList(String key, List value) {
        redisTemp.opsForList().leftPushAll(key, value);
    }

    /**
     * 保存list值至列表尾
     *
     * @param key
     * @param values
     */
    @Override
    public void rightListPush(String key, String... values) {
        redisTemp.opsForList().rightPushAll(key, values);
    }

    /**
     * 保存list至列表尾
     *
     * @param key
     * @param value
     */
    @Override
    public void rightSetList(String key, List value) {
        redisTemp.opsForList().rightPushAll(key, value);
    }

    /**
     * 弹出列表头元素
     *
     * @param key
     * @return
     */
    @Override
    public String leftListPop(String key) {
        return redisTemp.opsForList().leftPop(key);
    }

    /**
     * 弹出列表尾元素
     *
     * @param key
     * @return
     */
    @Override
    public String rightListPop(String key) {
        return redisTemp.opsForList().rightPop(key);
    }

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
    @Override
    public void ListRemove(String key, long count, Object value) {
        redisTemp.opsForList().remove(key, count, value);
    }
}
