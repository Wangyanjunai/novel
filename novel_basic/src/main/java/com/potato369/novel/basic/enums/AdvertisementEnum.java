package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 类名: AdvertisementEnum
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年5月6日 下午4:49:14
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum AdvertisementEnum implements CodeEnum<Object> {
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.enums.CodeEnum#getCode()
	 * </pre>
	 */

	TAG1_TIAOZHUANG(0, Boolean.TRUE, "跳转广告"),
	
	TAG1_APPLICATION(1, Boolean.TRUE, "应用内广告"),
	
	TAG2_TIAOZHUANG(0, Boolean.TRUE, "可以跳转"),
	
	TAG2_NO_TIAOZHUANG(1, Boolean.FALSE, "不可以跳转"),
	;
	
	private Integer code;
	
    private Boolean status;

    private String message;

}
