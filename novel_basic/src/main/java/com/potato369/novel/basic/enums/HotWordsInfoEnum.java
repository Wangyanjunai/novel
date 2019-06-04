package com.potato369.novel.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.enums
 * @EnumName HotWordsInfoEnum
 * @Desc 热词搜索默认值设置枚举
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 15:35
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public enum HotWordsInfoEnum implements CodeEnum<Object>{
	
	NEW(0, "新建"),
	
	OLD(1, "已有"),
	;
	private Integer code;
	
    private String message;
}
