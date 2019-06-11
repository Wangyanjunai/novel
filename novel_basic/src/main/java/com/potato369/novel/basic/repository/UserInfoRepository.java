package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface UserInfoRepository extends JpaRepository<NovelUserInfo, String> {

    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.mId = ?1")
    NovelUserInfo selectByUserMId(String userMId);
    
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.meId = ?1")
    NovelUserInfo selectByUserMeId(String userMeId);
    
    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.openid = ?1")
    NovelUserInfo selectByUserOpenid(String userOpenid);

    @Query(value = "SELECT nu FROM NovelUserInfo nu WHERE nu.meId = ?1 AND nu.openid = ?2")
    NovelUserInfo selectByUserMeIdAndUserOpenid(String meId, String openid);

    NovelUserInfo findUserInfoByOpenid(String openid);

    List<NovelUserInfo> findUserInfosByGender(String gender);
}
