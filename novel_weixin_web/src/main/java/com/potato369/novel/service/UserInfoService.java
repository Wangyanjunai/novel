package com.potato369.novel.service;

import com.potato369.novel.basic.dataobject.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName UserInfoService
 * @Desc 用户信息业务Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 17:32
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface UserInfoService {

    /**
     * 保存用户信息
     * @param userInfo
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    UserInfo save(UserInfo userInfo);

    /**
     * 保存用户信息列表
     * @param userInfoList
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<UserInfo> save(List<UserInfo> userInfoList);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    void del(String id);

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    UserInfo update(UserInfo userInfo);

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    UserInfo findById(String id);

    /**
     * 根据openid查找用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);

    /**
     * 查询所有用户信息
     * @return
     */
    List<UserInfo> findAll();

    /**
     * 根据性别查询所有的用户信息
     * @param sex
     * @return
     */
    List<UserInfo> findBySex(String sex);

    /**
     * 分页查询所有的用户信息
     * @param pageable
     * @return
     */
    Page<UserInfo> findAll(Pageable pageable);
}
