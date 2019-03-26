/**
 * <pre>
 * Project Name: novel
 * File Name: CategoriesDataVO.java 
 * Package Name: com.potato369.novel.vo
 * Create Date: 2018年12月18日 上午10:09:12
 * Copyright (c) 2018, 版权所有 (C) 2016-2036 土豆互联科技(深圳)有限公司 www.potato369.com All Rights Reserved
 * </pre>
 */
package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName CategoriesDataVO
 * @Desc 描述此类实现的功能作用
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018年12月18日 上午10:09:12
 * @CreateBy Eclipse IDEA Neon.3 Release(4.6.3)
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技（深圳）有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class CategoriesDataVO implements Serializable{

	private static final long serialVersionUID = -8472941048729899673L;

	@JsonProperty(value = "category")
	private Integer category;
	
	@JsonProperty(value = "num", defaultValue = "0")
	private BigDecimal number;
	
	@JsonProperty(value = "img1")
	private String imageUrl1;
	
	@JsonProperty(value = "img2")
	private String imageUrl2;
}

