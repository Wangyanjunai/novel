package com.potato369.novel.task;

import com.potato369.novel.basic.enums.OrderStatusEnum;
import com.potato369.novel.basic.enums.PayStatusEnum;
import com.potato369.novel.dto.OrderDTO;
import com.potato369.novel.service.OrderService;
import com.potato369.novel.utils.DateUtil;
import com.potato369.novel.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.task
 * @ClassName CancelsUnpaidTimeoutOrderTask1
 * @Desc CancelsUnpaidTimeoutOrderTask1
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/3/22 14:40
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Component
@Slf4j
public class CancelUnpaidTimeoutOrderTask {


    @Autowired
    private OrderService orderService;

    public void cancelsUnpaidTimeoutOrderTask() {
        try {
            if(log.isDebugEnabled()) {
                log.debug("【取消超时未支付订单定时任务】执行开始时间：{}", DateUtil.getTimeCN());
            }
            List<OrderDTO> orderDTOList = orderService.findTimeOutUnpaid(PayStatusEnum.PAY_WAITING.getCode(), OrderStatusEnum.RECHARGE_WAITING.getCode());
            Calendar calendar = Calendar.getInstance();
            String s1 = new StringBuffer().append(DateUtil.strFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss")).toString();//系统当前时间，既是定时任务执行的时间
            for (OrderDTO orderDTO:orderDTOList) {
                calendar.setTime(orderDTO.getCreateTime());
                calendar.add(Calendar.MINUTE, 30);
                String s2 = new StringBuffer().append(DateUtil.strFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss")).toString();//订单创建时间+30分钟
                if (DateUtil.compareDate(s1, s2)) {
                    if (log.isDebugEnabled()) {
                        log.debug("【取消超时未支付订单定时任务】订单信息={}", JsonUtil.toJson(orderDTO));
                    }
                    orderService.closeOrCancelOrder(orderDTO);
                    if(log.isDebugEnabled()) {
                        log.debug("【取消超时未支付订单定时任务】执行成功时间：{}", DateUtil.getTimeCN());
                    }
                }
            }
        } catch (Exception e) {
            log.error("【取消超时未支付订单定时任务】执行失败时间：{}", DateUtil.getTimeCN(), e);
        } finally {
            if(log.isDebugEnabled()) {
                log.debug("【取消超时未支付订单定时任务】执行结束时间：{}", DateUtil.getTimeCN());
            }
        }
    }
}
