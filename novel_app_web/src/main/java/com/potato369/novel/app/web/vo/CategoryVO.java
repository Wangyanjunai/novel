package com.potato369.novel.app.web.vo;

import java.util.List;

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
public class CategoryVO {

//	@JsonProperty(value = "id")
//	public String id;
//
//	@JsonProperty(value = "name")
//	public String categoryName;
	
	@JsonProperty(value = "DataList")
	public List<CategoryInfoVO> categoryInfoVOs;
}
