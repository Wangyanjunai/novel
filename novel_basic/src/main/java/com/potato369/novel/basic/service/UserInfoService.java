package com.potato369.novel.basic.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * @return NovelUserInfo
     */
    NovelUserInfo save(NovelUserInfo userInfo);

    /**
     * 保存用户信息列表
     * @param userInfoList
     * @return List<NovelUserInfo>
     */
    List<NovelUserInfo> save(List<NovelUserInfo> userInfoList);

    /**
     * 删除用户信息
     * @param mid
     * @return
     */
    void delete(String mid);

    /**
     * 修改用户信息
     * @param userInfo
     * @return NovelUserInfo
     */
    NovelUserInfo update(NovelUserInfo userInfo);

    /**
     * 根据用户mid查找用户信息
     * @param mid
     * @return NovelUserInfo
     */
    NovelUserInfo findById(String mid);

    /**
     * 根据meId（手机串码）查找用户信息
     * @param userMId
     * @return NovelUserInfo
     */
    NovelUserInfo findByUserMId(String userMId);
    
    /**
     * 根据meId手机串号查找用户信息列表
     * @param userMeId
     * @return List<NovelUserInfo>
     */
    List<NovelUserInfo> findByUserMeId(String userMeId);
    
    /**
     * 根据openid查找用户信息
     * @param openid
     * @return NovelUserInfo
     */
    NovelUserInfo findByOpenid(String openid);
    
    /**
     * 根据性别查询所有的用户信息
     * @param sex
     * @return List<NovelUserInfo>
     */
    List<NovelUserInfo> findBySex(String sex);
    
    /**
     * 根据meId手机串号和用户类型查找用户信息
     * @param userMeId
     * @param userType
     * @return NovelUserInfo
     */
    NovelUserInfo findByUserMeIdAndUserType(String userMeId, Integer userType);
    
    /**
     * 根据meId手机串号和用户平台id查找用户信息
     * @param meId
     * @return NovelUserInfo
     */
    NovelUserInfo findByMeIdAndOpenid(String meId, String openid);
    
    /**
	 * <pre>
	 * 根据用户登录平台openid和用户登录平台身份类型查找用户信息
	 * @param openid
	 * @param userType
	 * @return NovelUserInfo
	 * </pre>
	 */
    NovelUserInfo findByOpenidAndUserType(String openid, Integer userType);
    
    /**
     * 根据meId手机串号，平台openid和用户登录身份类型查找用户信息
     * @param meId
     * @param openid
     * @param userType
     * @return NovelUserInfo
     */
    NovelUserInfo findByMeIdAndOpenidAndUserType(String meId, String openid, Integer userType);

    /**
     * 查询所有用户信息
     * @return List<NovelUserInfo>
     */
    List<NovelUserInfo> findAll();

    /**
     * 分页查询所有的用户信息列表
     * @param pageable
     * @return Page<NovelUserInfo>
     */
    Page<NovelUserInfo> findAll(Pageable pageable);
}
