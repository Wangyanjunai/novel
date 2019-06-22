package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.enums
 * @EnumName OrderStatusEnum
 * @Desc 订单状态枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:41
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum OrderStatusEnum implements CodeEnum<Object> {

    RECHARGE_WAITING(0, "等待充值"),

    RECHARGE_SUCCESS(1, "充值成功"),

    RECHARGE_FAIL(2, "充值失败"),

    RECHARGE_CLOSE(3, "充值关闭"),
    
    EXCHANGE_WAITING(4, "等待兑换"),
    
    EXCHANGE_SUCCESS(5, "兑换成功"),

    EXCHANGE_FAIL(6, "兑换失败"),

    EXCHANGE_CLOSE(7, "兑换关闭"),

    WITHDRAW_ING(8, "等待提现"),
    
    WITHDRAW_SUCCESS(9, "提现成功"),

    WITHDRAW_CLOSE(10, "提现关闭"),
    
    WITHDRAW_FAIL(11, "提现失败"),
    ;
    private Integer code;

    private String message;
}
