package com.potato369.novel.service.impl;

import com.potato369.novel.basic.dataobject.OrderDetail;
import com.potato369.novel.conf.prop.WeChatMpProperties;
import com.potato369.novel.dto.OrderDTO;
import com.potato369.novel.dto.SellerInfoDTO;
import com.potato369.novel.service.PushMessageService;
import com.potato369.novel.utils.DateUtil;
import com.potato369.novel.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName PushMessageServiceImpl
 * @Desc 发送公众号模板消息业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 11:41
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WeChatMpProperties wechatAccountConfig;

    /**
     * 推送订单状态变更模板消息
     *
     * @param orderDTO
     * @param first
     * @param remark
     */
    @Override
    public void pushOrderStatus(OrderDTO orderDTO, String first, String remark) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(wechatAccountConfig.getOrderStatus());
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", first,"red"),
                new WxMpTemplateData("keyword1", DateUtil.getTimeCN()),
                new WxMpTemplateData("keyword2", orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("remark", remark, "green"));
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("【推送订单状态变更模板消息】成功，templateMessage={}", JsonUtil.toJson(templateMessage));
        } catch (Exception e) {
            log.error("【推送订单状态变更模板消息】失败，出现错误 e={}" , e);
        }
    }

    /**
     * 推送下单成功模板消息
     *
     * @param orderDTO
     */
    @Override
    public void pushOrderSuccess(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(wechatAccountConfig.getOrderSuccess());
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        StringBuffer orderProductNames = new StringBuffer();
        for(OrderDetail orderDetail:orderDetailList) {
            orderProductNames.append(orderDetail.getProductName()).append(" ");
        }
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","恭喜您，订单下单成功通知！！！","red"),
                new WxMpTemplateData("keyword1", orderProductNames.toString()),
                new WxMpTemplateData("keyword2", "￥"+ orderDTO.getOrderAmount()),
                new WxMpTemplateData("keyword3", DateUtil.getTimeCN()),
                new WxMpTemplateData("remark","我们将立即安排发放【"+ orderProductNames.toString().trim()+"】到您的充值账户","green"));
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("【推送下单成功模板消息】成功，templateMessage={}", JsonUtil.toJson(templateMessage));
        } catch (Exception e) {
            log.error("【推送下单成功模板消息】失败，出现错误 e={}" , e);
        }
    }

    /**
     * <pre>
     * 推送买家端订单取消通知微信模板消息
     * @param orderDTO
     * </pre>
     */
    @Override
    public void pushOrderCancel(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(wechatAccountConfig.getOrderCancel());
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","您的订单已经取消，请您知悉！！！","red"),
                new WxMpTemplateData("keyword1", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword2", DateUtil.sdfTimeCN.format(orderDTO.getCreateTime())),
                new WxMpTemplateData("remark","如果您支付成功了，订单金额将退回您的账户，请您查收！！！","green"));
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("【推送订单取消通知模板消息】成功，templateMessage={}", JsonUtil.toJson(templateMessage));
        } catch (Exception e) {
            log.error("【推送订单取消通知模板消息】失败，出现错误 e={}" , e);
        }
    }

    /**
     * <pre>
     * 推送订单支付成功模板消息
     * @param orderDTO
     * </pre>
     */
    @Override
    public void pushPaySuccess(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(wechatAccountConfig.getPaySuccess());
        templateMessage.setToUser(orderDTO.getBuyerOpenid());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","您好，您的订单已支付成功！！！","red"),
                new WxMpTemplateData("keyword1", orderDTO.getOrderId()),
                new WxMpTemplateData("keyword2", DateUtil.getTimeCN()),
                new WxMpTemplateData("keyword3", "￥" + orderDTO.getOrderAmount()),
                new WxMpTemplateData("keyword4", "微信支付"),
                new WxMpTemplateData("remark","感谢您对土豆互联的支持，有任何疑问可拨打客服电话：0755-86969315","green")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("【推送订单支付模板消息】成功，templateMessage={}", JsonUtil.toJson(templateMessage));
        }catch (Exception e){
            log.error("【推送订单支付模板消息】失败，出现错误 e={}" , e);
        }
    }

    /**
     * <pre>
     * 推送卖家管理员登录成功通知微信模板消息
     * @param sellerInfoDTO
     * </pre>
     */
    @Override
    public void pushSellerLoginSuccess(SellerInfoDTO sellerInfoDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(wechatAccountConfig.getSellerLoginSuccess());
        templateMessage.setToUser(sellerInfoDTO.getOpenid());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first",sellerInfoDTO.getSellerName() + "：您好，您已经登录后台管理！！！","red"),
                new WxMpTemplateData("keyword1", "登录后台管理成功，登录IP：" + sellerInfoDTO.getLoginIp()),
                new WxMpTemplateData("keyword2", DateUtil.sdfTimeCN.format(sellerInfoDTO.getLoginTime())),
                new WxMpTemplateData("remark","感谢你的使用，后台操作请谨慎！！！","green"));
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            log.info("【推送登录成功通知模板消息】成功，templateMessage={}", JsonUtil.toJson(templateMessage));
        } catch (Exception e) {
            log.error("【推送登录成功通知模板消息】失败，出现错误 e={}" , e);
        }
    }
}
