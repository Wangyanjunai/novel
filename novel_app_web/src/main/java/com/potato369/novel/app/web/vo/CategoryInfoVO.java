package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 类名: CategoryVO
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月28日 上午10:58:12
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CategoryInfoVO {

	@JsonProperty(value = "id")
	public String id;

//	@JsonProperty(value = "parentId")
//	public String parentId;

	@JsonProperty(value = "categoryName")
	public String name;
//
//	@JsonProperty(value = "bookCount")
//	public Integer bookCount;
//
//	@JsonProperty(value = "monthlyCount")
//	public Integer monthlyCount;
//
//	@JsonProperty(value = "icon")
//	public String icon;
//
//	@JsonProperty(value = "bookCover")
//	public String[] bookCover;
}
