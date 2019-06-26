package com.potato369.novel.app.web.converter;

import com.potato369.novel.app.web.vo.UserInfoVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import org.springframework.beans.BeanUtils;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.converter
 * @ClassName UserInfo2UserInfoVOConverter
 * @Desc UserInfo2UserInfoVOConverter
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 15:01
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class UserInfo2UserInfoVOConverter {

    /**
     * <pre>
     * convert方法的作用：将NovelUserInfo对象转换为UserInfoVO对象
     * @author Jack
     * @param userInfo
     * @return UserInfoVO
     * @since JDK 1.6
     * </pre>
     */
    public static UserInfoVO convert(NovelUserInfo userInfo) {
        UserInfoVO userInfoVO = UserInfoVO.builder().build();
        BeanUtils.copyProperties(userInfo, userInfoVO);
        return userInfoVO;
    }

    /**
     * <pre>
     * convert方法的作用：将UserInfoVO对象转换为NovelUserInfo对象
     * @author Jack
     * @param userInfoVO
     * @return NovelUserInfo
     * @since JDK 1.6
     * </pre>
     */
    public static NovelUserInfo convert(UserInfoVO userInfoVO) {
        NovelUserInfo userInfo = NovelUserInfo.builder().build();
        BeanUtils.copyProperties(userInfoVO, userInfo);
        return userInfo;
    }
}
