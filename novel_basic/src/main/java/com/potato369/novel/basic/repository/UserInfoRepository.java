package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @ClassName UserInfoRepository
 * @Desc 用户信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 17:30
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Repository
public interface UserInfoRepository extends JpaRepository<NovelUserInfo, String> {

    /**
     * <pre>
     * selectByUserMId方法的作用：根据用户mid查找用户信息
     * @param mId
     * @return NovelUserInfo
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.mId = ?1 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    NovelUserInfo selectByUserMId(String mId);

    /**
     * <pre>
     * selectByUserMeId方法的作用：根据用户meId（手机串号）查找用户信息
     * @param meId
     * @return List<NovelUserInfo>
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.meId = ?1 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    List<NovelUserInfo> selectByUserMeId(String meId);

    /**
     * <pre>
     * selectByUserOpenid方法的作用：根据用户登录平台openid查找用户信息
     * @param openid
     * @return NovelUserInfo
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.openid = ?1 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    NovelUserInfo selectByUserOpenid(String openid);

    /**
     * <pre>
     * selectByUserGender方法的作用：根据用户性别查找用户信息列表
     * @param gender
     * @return List<NovelUserInfo>
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.gender = ?1 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    List<NovelUserInfo> selectByUserGender(String gender);

    /**
     * <pre>
     * selectByUserMeIdAndUserType方法的作用：根据用户meId（手机串号）和用户登录平台身份类型查找用户信息
     * @param meId
     * @param userType
     * @return NovelUserInfo
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.meId = ?1 AND nu.userType = ?2 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    NovelUserInfo selectByUserMeIdAndUserType(String meId, Integer userType);

    /**
     * <pre>
     * selectByUserMeIdAndUserOpenid方法的作用：根据用户meId（手机串号）和用户登录平台openid查找用户信息
     * @param meId
     * @param openid
     * @return NovelUserInfo
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.meId = ?1 AND nu.openid = ?2 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    NovelUserInfo selectByUserMeIdAndUserOpenid(String meId, String openid);

    /**
     * <pre>
     * selectByUserOpenidAndUserType方法的作用：根据用户登录平台openid和用户登录平台身份类型查找用户信息
     * @param openid
     * @param userType
     * @return NovelUserInfo
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.openid = ?1 AND nu.userType = ?2 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    NovelUserInfo selectByUserOpenidAndUserType(String openid, Integer userType);

    /**
     * <pre>
     * selectByUserMeIdAndUserOpenidAndUserType方法的作用：根据用户meId（手机串号），用户登录平台openid和用户登录平台身份类型查找用户信息
     * @param meId
     * @param openid
     * @param userType
     * @return NovelUserInfo
     * </pre>
     */
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.meId = ?1 AND nu.openid = ?2 AND nu.userType = ?3 ORDER BY nu.loginTime DESC, nu.createTime DESC")
    NovelUserInfo selectByUserMeIdAndUserOpenidAndUserType(String meId, String openid, Integer userType);
}
