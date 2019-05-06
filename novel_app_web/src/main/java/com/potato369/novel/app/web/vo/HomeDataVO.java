package com.potato369.novel.app.web.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class HomeDataVO {

	@JsonProperty(value = "bannerAds")
	private List<LoadingDataVO> bannerAdDataVOs;
}
