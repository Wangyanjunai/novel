package com.potato369.novel.converter;

import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.UUIDUtil;
import com.potato369.novel.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.converter
 * @ClassName WxMpUser2UserInfoConverter
 * @Desc 微信用户信息转换为数据库用户信息转化器
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/03 11:44
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
public class WxMpUser2UserInfoConverter {

    /**
     * <pre>
     * 将List<WxMpUser>对象转换为List<UserInfo>对象
     * @param userInfoService
     * @param wxMpUserList
     * @return List<UserInfo>对象
     * </pre>
     */
    public static List<NovelUserInfo> WxMpUserList2UserInfoListConvert(UserInfoService userInfoService, List<WxMpUser> wxMpUserList) {
        List<NovelUserInfo> userInfoList = new ArrayList<NovelUserInfo>();
        try {
            for (WxMpUser wxMpUser : wxMpUserList) {
            	NovelUserInfo userInfo = userInfoService.findByOpenid(wxMpUser.getOpenId());
                if (userInfo == null) {
                    userInfo = WxMpUser2UserInfoConvert(wxMpUser);
                    userInfoList.add(userInfo);
                }
            }
            return userInfoList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <pre>
     * 将WxMpUser对象转换为UserInfo对象
     * @param wxMpUser
     * @return UserInfo对象
     * </pre>
     */
    public static NovelUserInfo WxMpUser2UserInfoConvert(WxMpUser wxMpUser) {
    	NovelUserInfo userInfo = NovelUserInfo.builder().build();
        userInfo.setMId(UUIDUtil.gen13MID());
        userInfo.setAddress(wxMpUser.getCountry()+wxMpUser.getProvince()+wxMpUser.getCity());
//        userInfo.setProvince(wxMpUser.getProvince());
//        userInfo.setCity(wxMpUser.getCity());
        userInfo.setLang(wxMpUser.getLanguage());
        userInfo.setGender(wxMpUser.getSex());
//        userInfo.setSubscribe(getSubscribeValue(wxMpUser.getSubscribe()));
        userInfo.setOpenid(wxMpUser.getOpenId());
        userInfo.setNickName(String.valueOf(wxMpUser.getNickname()));
//        userInfo.setUnionId(wxMpUser.getUnionId());
        userInfo.setAvatarUrl(wxMpUser.getHeadImgUrl());
//        userInfo.setSubscribeTime(new Date(wxMpUser.getSubscribeTime() * 1000L));
        userInfo.setBalanceAmount(BigDecimal.ZERO);
        if (log.isDebugEnabled()) {
        	log.debug("userInfo={}", JsonUtil.toJson(userInfo));
		}
        return userInfo;
    }

    /**
     * <pre>
     * 将是否关注转换为对应的数字
     * @param subscribe
     * @return Integer数字
     * </pre>
     */
    public static Integer getSubscribeValue(boolean subscribe) {
        return subscribe == true ? 1 : 0;
    }
}
