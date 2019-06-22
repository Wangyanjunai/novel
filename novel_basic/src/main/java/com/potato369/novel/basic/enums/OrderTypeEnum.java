package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
    * 类名: OrderTypeEnum
   * 类的作用:
   * 创建原因:
   * 创建时间: 2019年6月4日 下午2:31:09
   * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum OrderTypeEnum implements CodeEnum<Object>{
	
	WITHDRAW(0, "提现交易"),

	EXCHANGE(1, "兑换交易"),

    RECHARGE(2, "充值交易"),
    ;
    
    private Integer code;

    private String message;

}
