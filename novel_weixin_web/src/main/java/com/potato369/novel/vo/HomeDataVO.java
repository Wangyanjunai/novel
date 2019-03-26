/**
 * <pre>
 * Project Name: novel
 * File Name: HomeDataVO.java 
 * Package Name: com.potato369.novel.vo
 * Create Date: 2018年12月18日 上午9:47:28
 * Copyright (c) 2018, 版权所有 (C) 2016-2036 土豆互联科技(深圳)有限公司 www.potato369.com All Rights Reserved
 * </pre>
 */
package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName HomeDataVO
 * @Desc 描述此类实现的功能作用
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018年12月18日 上午9:47:28
 * @CreateBy Eclipse IDEA Neon.3 Release(4.6.3)
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技（深圳）有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class HomeDataVO implements Serializable {
 
	private static final long serialVersionUID = -524293234325233269L;

	@JsonProperty(value = "banner")
	private String bannerUrl;

	@JsonProperty(value = "banners")
	private List<BannerInfoVO> bannerInfoListData;
	
	@JsonProperty(value = "guessYouLike")
	private List<NovelInfoVO> guessYouLikeData;
	
	@JsonProperty(value = "recommend")
	private List<NovelInfoVO> recommendData;
	
	@JsonProperty(value = "featured")
	private List<NovelInfoVO> featuredData;
	
	@JsonProperty(value = "random")
	private List<NovelInfoVO> randomData;
	
	@JsonProperty(value = "categoryList")
	private List<CategoryDataVO> categoryListData;
	
	@JsonProperty(value = "categories")
	private List<CategoriesDataVO> categoriesData;
}

