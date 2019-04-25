package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 类名: LoadingDataVO
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月18日 上午10:55:21
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class LoadingDataVO {

	/**
	 * <pre>
	 * id:广告id
	 * </pre>
	 */
	@JsonProperty(value="id", required=true)
	private String id;

	/**
	 * <pre>
	 * tag:标识
	 * </pre>
	 */
	@JsonProperty(value="tag", required=true)
	private Integer tag;

	/**
	 * <pre>
	 * imageUrl:图片链接地址
	 * </pre>
	 */
	@JsonProperty(value="imageUrl", required=true)
	private String imageUrl;

	/**
	 * <pre>
	 * linkUrl:广告跳转链接地址
	 * </pre>
	 */
	@JsonProperty(value="linkUrl", required=false)
	private String linkUrl;
}
