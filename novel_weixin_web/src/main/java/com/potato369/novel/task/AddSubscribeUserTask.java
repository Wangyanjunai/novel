package com.potato369.novel.task;

import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.converter.WxMpUser2UserInfoConverter;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * @PackageName com.potato369.novel.task
 * @ClassName AddSubscribeUserTask1
 * @Desc AddSubscribeUserTask1
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/3/22 14:40
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Component
@Slf4j
public class AddSubscribeUserTask {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private UserInfoService userInfoService;

    public void addSubscribeUserTask() {
        try {
            if(log.isDebugEnabled()) {
                log.debug("【添加关注用户信息到数据库动态定时任务】执行开始时间：{}", DateUtil.getTimeCN());
            }
            WxMpUserList wxMpUserList = wxMpService.getUserService().userList(null);
            for (String openid:wxMpUserList.getOpenids()) {
                NovelUserInfo userInfo = userInfoService.findByOpenid(openid);
                if (userInfo == null) {
                    WxMpUser wxMpUser = wxMpService.getUserService().userInfo(openid);
                    userInfoService.save(WxMpUser2UserInfoConverter.WxMpUser2UserInfoConvert(wxMpUser));
                    if(log.isDebugEnabled()) {
                        log.debug("【添加关注用户信息到数据库动态定时任务】执行成功时间：{}", DateUtil.getTimeCN());
                    }
                }
            }
        } catch (Exception e) {
            log.error("【添加关注用户信息到数据库动态定时任务】执行失败时间：{}", DateUtil.getTimeCN(), e);
        } finally {
            if(log.isDebugEnabled()) {
                log.debug("【添加关注用户信息到数据库动态定时任务】执行结束时间：{}", DateUtil.getTimeCN());
            }
        }
    }
}
