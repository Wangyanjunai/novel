package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @ClassName UserInfoRepository
 * @Desc 用户信息Repository数据库操作类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/25 17:30
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findUserInfoByOpenid(String openid);

    List<UserInfo> findUserInfosByGender(String gender);
}
