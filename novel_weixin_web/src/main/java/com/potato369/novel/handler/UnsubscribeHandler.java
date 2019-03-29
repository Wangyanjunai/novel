package com.potato369.novel.handler;

import com.potato369.novel.basic.dataobject.UserInfo;
import com.potato369.novel.basic.service.UserInfoService;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {

  @Autowired
  private UserInfoService userInfoService;

  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
    String openId = wxMessage.getFromUser();
    this.logger.info("取消关注用户 OPENID: " + openId);
    try {
      UserInfo userInfo = userInfoService.findByOpenid(openId);
      if (userInfo != null) {
        userInfo.setSubscribe(0);
      }
      userInfoService.save(userInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
