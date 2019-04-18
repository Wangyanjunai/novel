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
	 * 
	 * </pre>
	 */
	@JsonProperty(value="id", required=true)
	private String id;
	
	@JsonProperty(value="tag", required=true)
	private Integer tag;
	
	@JsonProperty(value="imageUrl", required=true)
	private String imageUrl;
	
	@JsonProperty(value="linkUrl", required=false)
	private String linkUrl;
}
