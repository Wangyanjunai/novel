package com.potato369.novel.app.web.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * <pre>
 * 类名: HomeDataVO
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年5月6日 下午4:13:37
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Data
@Builder
public class HomeDataVO {

	@JsonProperty(value = "bannerAds")//轮播图广告
	private List<LoadingDataVO> bannerAdDataVOs;
	
	@JsonProperty(value = "hotYouLikes")//近期热门
	private List<NovelInfoVO> hotYouLikeData;
	
	@JsonProperty(value = "editorRecommends")//主编推荐
	private List<NovelInfoVO> editorRecommendData;
	
	@JsonProperty(value = "newestRecommends")//新书推荐
	private List<NovelInfoVO> newestData;
	
	@JsonProperty(value = "featured")//爽文推荐
	private NovelInfoVO featuredData;
}
