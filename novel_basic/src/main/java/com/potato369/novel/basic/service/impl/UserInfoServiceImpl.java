package com.potato369.novel.basic.service.impl;

import java.util.List;

import com.potato369.novel.basic.dataobject.idClass.NovelUserInfoIdClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.repository.UserInfoRepository;
import com.potato369.novel.basic.service.UserInfoService;

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
    public NovelUserInfo save(NovelUserInfo userInfo){
        return repository.save(userInfo);
    }

    /**
     * 保存用户信息列表
     *
     * @param userInfoList
     * @return
     */
    @Override
    public List<NovelUserInfo> save(List<NovelUserInfo> userInfoList){
        return repository.save(userInfoList);
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @Override
    public void del(NovelUserInfoIdClass id){
        repository.delete(id);
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return
     */
    @Override
    public NovelUserInfo update(NovelUserInfo userInfo){
        return repository.saveAndFlush(userInfo);
    }

    /**
     * 根据id查找用户信息
     *
     * @param id
     * @return
     */
    @Override
    public NovelUserInfo findById(NovelUserInfoIdClass id){
        return repository.findOne(id);
    }

    /**
     * 根据用户id查找用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public NovelUserInfo findByUserId(String userId) {
        return repository.selectByUserId(userId);
    }

    /**
     * 根据openid查找用户信息
     *
     * @param openid
     * @return
     */
    @Override
    public NovelUserInfo findByOpenid(String openid){
        return repository.findUserInfoByOpenid(openid);
    }

    /**
     * 查询所有用户信息
     *
     * @return
     */
    @Override
    public List<NovelUserInfo> findAll(){
        return repository.findAll();
    }

    /**
     * 根据性别查询所有的用户信息
     *
     * @param gender
     * @return
     */
    @Override
    public List<NovelUserInfo> findBySex(String gender){
        return repository.findUserInfosByGender(gender);
    }

    /**
     * 分页查询所有的用户信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<NovelUserInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
