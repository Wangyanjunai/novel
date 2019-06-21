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

    NEW(0, "交易创建并等待买家付款"),

    SUCCESS(1, "交易支付成功"),

    CLOSE(2, "未付款交易超时关闭或支付完成后全额退款"),

    FAIL(3, "交易结束并不可退款"),
    
    EXCHANG_ING(3, "等待兑换"),
    
    EXCHANGE_SUCCESSED(4, "兑换成功"),
    
    EXCHANGE_FAILED(5, "兑换失败"),
    
    WITHDRAW_ING(6, "等待提现"),
    
    WITHDRAW_SUCCESSED(7, "提现成功"),
    
    WITHDRAW_FAILED(8, "提现失败"),
    ;
    private Integer code;

    private String message;
}
