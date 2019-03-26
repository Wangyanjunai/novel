package com.potato369.novel.service;

import com.potato369.novel.dto.OrderDTO;
import com.potato369.novel.dto.SellerInfoDTO;

/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName PushMessageService
 * @Desc 发送公众号模板消息业务Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 11:38
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface PushMessageService {

    /**
     * <pre>
     * 推送买家端订单状态变更模板消息
     * @param orderDTO
     * </pre>
     */
    void pushOrderStatus(OrderDTO orderDTO, String first, String remark);

    /**
     * <pre>
     * 推送买家端下单成功通知微信模板消息
     * @param orderDTO
     * </pre>
     */
    void pushOrderSuccess(OrderDTO orderDTO);

    /**
     * <pre>
     * 推送买家端订单取消通知微信模板消息
     * @param orderDTO
     * </pre>
     */
    void pushOrderCancel(OrderDTO orderDTO);

    /**
     * <pre>
     * 推送买家端订单支付成功通知微信模板消息
     * @param orderDTO
     * </pre>
     */
    void pushPaySuccess(OrderDTO orderDTO);

    /**
     * <pre>
     * 推送卖家管理员登录成功通知微信模板消息
     * @param sellerInfoDTO
     * </pre>
     */
    void pushSellerLoginSuccess(SellerInfoDTO sellerInfoDTO);
}
