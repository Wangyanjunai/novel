package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.idClass.NovelUserInfoIdClass;
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
public interface UserInfoRepository extends JpaRepository<NovelUserInfo, NovelUserInfoIdClass> {

    @Query(value = "select nu from NovelUserInfo nu where nu.mId = ?1")
    NovelUserInfo selectByUserId(String userId);

    @Query(value = "select nu from NovelUserInfo nu where nu.meId = ?1 or nu.openid = ?2")
    NovelUserInfo selectByUserMeIdOrUserOpenid(String meId, String openid);

    NovelUserInfo findUserInfoByOpenid(String openid);

    List<NovelUserInfo> findUserInfosByGender(String gender);
}
