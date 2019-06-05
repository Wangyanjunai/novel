package com.potato369.novel.app.web.converter;

import com.potato369.novel.app.web.dto.UserInfoDTO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import org.springframework.beans.BeanUtils;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.converter
 * @ClassName UserInfo2UserInfoDTOConverter
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 15:01
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class UserInfo2UserInfoDTOConverter {

    public static UserInfoDTO convert(NovelUserInfo userInfo) {
        UserInfoDTO userInfoDTO = UserInfoDTO.builder().build();
        BeanUtils.copyProperties(userInfo, userInfoDTO);
        return userInfoDTO;
    }

    public static NovelUserInfo convert(UserInfoDTO userInfoDTO) {
        NovelUserInfo userInfo = NovelUserInfo.builder().build();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        return userInfo;
    }
}
