package com.potato369.novel.basic.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.repository.UserInfoRepository;
import com.potato369.novel.basic.service.UserInfoService;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository repository;

    /**
     * 保存用户信息
     *
     * @param userInfo
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo save(NovelUserInfo userInfo){
        return repository.save(userInfo);
    }

    /**
     * 保存用户信息列表
     *
     * @param userInfoList
     * @return NovelUserInfo
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
    public void delete(String id){
        repository.delete(id);
    }

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo update(NovelUserInfo userInfo){
        return repository.saveAndFlush(userInfo);
    }

    /**
     * 根据id查找用户信息
     *
     * @param id
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo findById(String mid){
        return repository.findOne(mid);
    }

    /**
     * 根据用户id查找用户信息
     *
     * @param userId
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo findByUserMId(String userMId) {
        return repository.selectByUserMId(userMId);
    }
    /**
     * 根据meId手机串号查找用户信息
     * @param userMeId
     * @return List<NovelUserInfo>
     */
    @Override
    public List<NovelUserInfo> findByUserMeId(String userMeId) {
    	return repository.selectByUserMeId(userMeId);
    }
    
    /**
     * 根据meId手机串号和用户类型查找用户信息
     * @param userMeId
     * @param userType
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo findByUserMeIdAndUserType(String userMeId, Integer userType) {
    	return repository.selectByUserMeIdAndUserType(userMeId, userType);
    }

    /**
     * 根据meId手机串号和平台openid查找用户信息
     * @param meId
     * @param openid
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo findByMeIdAndOpenid(String meId, String openid) {
        return repository.selectByUserMeIdAndUserOpenid(meId, openid);
    }
    
    /**
     * 根据用户登录平台openid和用户登录平台身份类型查找用户信息
     * @param openid
     * @param userType
     * @return NovelUserInfo
     */
	@Override
	public NovelUserInfo findByOpenidAndUserType(String openid, Integer userType) {
		return repository.selectByUserOpenidAndUserType(openid, userType);
	}
	
    /**
     * 根据meId手机串号，平台openid和用户登录身份类型查找用户信息
     * @param meId
     * @param openid
     * @param userType
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo findByMeIdAndOpenidAndUserType(String meId, String openid, Integer userType) {
        return repository.selectByUserMeIdAndUserOpenidAndUserType(meId, openid, userType);
    }

    /**
     * 根据openid查找用户信息
     *
     * @param openid
     * @return NovelUserInfo
     */
    @Override
    public NovelUserInfo findByOpenid(String openid){
        return repository.selectByUserOpenid(openid);
    }

    /**
     * 查询所有用户信息
     *
     * @return List<NovelUserInfo>
     */
    @Override
    public List<NovelUserInfo> findAll(){
        return repository.findAll();
    }

    /**
     * 根据性别查询所有的用户信息
     *
     * @param gender
     * @return List<NovelUserInfo>
     */
    @Override
    public List<NovelUserInfo> findBySex(String gender){
        return repository.selectByUserGender(gender);
    }

    /**
     * 分页查询所有的用户信息
     *
     * @param pageable
     * @return Page<NovelUserInfo>
     */
    @Override
    public Page<NovelUserInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
