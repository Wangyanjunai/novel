package com.potato369.novel.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.potato369.novel.basic.dataobject.NovelUserInfo;

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
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    NovelUserInfo save(NovelUserInfo userInfo) throws Exception;

    /**
     * 保存用户信息列表
     * @param userInfoList
     * @return
     */
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    List<NovelUserInfo> save(List<NovelUserInfo> userInfoList) throws Exception;

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void del(String id) throws Exception;

    /**
     * 修改用户信息
     * @param userInfo
     * @return
     */
    @Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    NovelUserInfo update(NovelUserInfo userInfo) throws Exception;

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelUserInfo findById(String id) throws Exception;

    /**
     * 根据openid查找用户信息
     * @param openid
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelUserInfo findByOpenid(String openid) throws Exception;

    /**
     * 查询所有用户信息
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelUserInfo> findAll() throws Exception;

    /**
     * 根据性别查询所有的用户信息
     * @param sex
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelUserInfo> findBySex(String sex) throws Exception;

    /**
     * 分页查询所有的用户信息
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelUserInfo> findAll(Pageable pageable) throws Exception;
}
