package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.enums
 * @EnumName PayStatusEnum
 * @Desc 支付状态枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:40
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum PayStatusEnum implements CodeEnum<Object> {

    NEW(0, "等待支付"),

    SUCCESS(1, "支付成功"),

    CLOSE(2, "支付关闭"),

    FAIL(3, "支付失败"),
    
    EXCHANG_ING(4, "等待兑换"),
    
    EXCHANG_SUCCESS(5, "兑换成功"),
    
    EXCHANG_FAIL(6, "兑换失败"),

    EXCHANG_CLOSE(7, "兑换关闭"),
    
    WITHDRAW_ING(8, "等待提现"),
    
    WITHDRAW_SUCCESS(9, "提现成功"),
    
    WITHDRAW_FAIL(10, "提现失败"),

    WITHDRAW_CLOSE(11, "提现关闭"),
    ;

    private Integer code;

    private String message;

}
