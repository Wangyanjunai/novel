/**
 * <pre>
 * Project Name: novel
 * File Name: CategoryDataVO.java 
 * Package Name: com.potato369.novel.vo
 * Create Date: 2018年12月18日 上午10:05:05
 * Copyright (c) 2018, 版权所有 (C) 2016-2036 土豆互联科技(深圳)有限公司 www.potato369.com All Rights Reserved
 * </pre>
 */
package com.potato369.novel.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName CategoryDataVO
 * @Desc 描述此类实现的功能作用
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018年12月18日 上午10:05:05
 * @CreateBy Eclipse IDEA Neon.3 Release(4.6.3)
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技（深圳）有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class CategoryDataVO implements Serializable{
	
	private static final long serialVersionUID = -8095492849728277859L;

	@JsonProperty(value = "category")
	private Integer category;
	
	@JsonProperty(value = "list")
	private List<NovelInfoVO> listData;
}

