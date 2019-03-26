package com.potato369.novel.service.impl;

import com.potato369.novel.basic.dataobject.UserInfo;
import com.potato369.novel.basic.repository.UserInfoRepository;
import com.potato369.novel.service.UserInfoService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName UserInfoServiceImpl
 * @Desc 用户信息业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 17:36
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository repository;

    /**
     * 保存用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo save(UserInfo userInfo) {
        return repository.save(userInfo);
    }

    /**
     * 保存用户信息列表
     *
     * @param userInfoList
     * @return
     */
    @Override
    public List<UserInfo> save(List<UserInfo> userInfoList) {
        return repository.save(userInfoList);
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @Override
    public void del(String id) {
        repository.delete(id);
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public UserInfo update(UserInfo userInfo) {
        UserInfo userInfoTmp = UserInfo.builder().build();
        if(userInfo != null) {
            userInfoTmp.setId(userInfo.getId());
            BeanUtils.copyProperties(userInfo, userInfoTmp);
        }
        return repository.saveAndFlush(userInfoTmp);
    }

    /**
     * 根据id查找用户信息
     *
     * @param id
     * @return
     */
    @Override
    public UserInfo findById(String id) {
        return repository.findOne(id);
    }

    /**
     * 根据openid查找用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        return repository.findUserInfoByOpenid(openid);
    }

    /**
     * 查询所有用户信息
     *
     * @return
     */
    @Override
    public List<UserInfo> findAll() {
        return repository.findAll();
    }

    /**
     * 根据性别查询所有的用户信息
     *
     * @param gender
     * @return
     */
    @Override
    public List<UserInfo> findBySex(String gender) {
        return repository.findUserInfosByGender(gender);
    }

    /**
     * 分页查询所有的用户信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<UserInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
