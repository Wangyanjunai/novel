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
public enum PayTypeEnum implements CodeEnum<Object> {
    
    PAY_WITH_ALIPAY(1, "支付宝支付"),
    
    PAY_WITH_WECHAT(2, "微信支付"),
    
    PAY_WITH_BALANCE(3, "余额支付"),
    ;

    private Integer code;

    private String message;

}
