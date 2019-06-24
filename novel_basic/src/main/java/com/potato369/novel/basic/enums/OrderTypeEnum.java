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
	
	RECHARGE(0, "充值"),

	EXCHANGE(1, "兑换"),

    WITHDRAW(2, "提现"),
    ;
    
    private Integer code;

    private String message;

}
