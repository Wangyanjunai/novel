package com.potato369.novel.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.potato369.novel.builder.NewsBuilder;
import com.potato369.novel.converter.WxMpUser2UserInfoConverter;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.utils.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class SubscribeHandler extends AbstractHandler {

  @Autowired
  private UserInfoService userInfoService;

  /**
   * 一般处理
   * @param wxMessage
   * @param context
   * @param weixinService
   * @param sessionManager
   * @return
   * @throws WxErrorException
   */
  @Override
  public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService, WxSessionManager sessionManager) throws WxErrorException {


    if (this.logger.isDebugEnabled()) {
      this.logger.info("【普通（搜索或者消息推送关注的）】新关注用户 OPENID: " + wxMessage.getFromUser());
    }

    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();

    // 获取微信用户基本信息
    WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);
    if (this.logger.isDebugEnabled()) {
      this.logger.info("【新关注用户】  WxMpUser={}" + JsonUtil.toJson(userWxInfo));
    }
    userInfoService.save(WxMpUser2UserInfoConverter.WxMpUser2UserInfoConvert(userWxInfo));
    WxMpXmlOutMessage responseResult = null;
    try {
      responseResult = handleSpecial(wxMessage);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    if (responseResult != null) {
      return responseResult;
    }

    try {
      NewsBuilder newsBuilder = new NewsBuilder();
      WxMpXmlOutNewsMessage.Item item1 = new WxMpXmlOutNewsMessage.Item();
      item1.setTitle("亲，终于等到您了！推荐几本热门小说，给您看看：");
      item1.setDescription("");
      item1.setUrl("https://www.potato369.com");
      return newsBuilder.build("", wxMessage, weixinService);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }
    return null;
  }

  /**
   * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
   * @param wxMessage
   * @return
   * @throws Exception
   */
  private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)  throws Exception {
    return null;
  }
}
