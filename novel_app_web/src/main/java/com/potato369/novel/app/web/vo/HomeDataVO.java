package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class HomeDataVO {

    @JsonProperty(value = "DataList")//小说数据列表
    private List<NovelInfoVO> list;
//
//	@JsonProperty(value = "CurrentPage")//小说当前页
//	private BigDecimal currentPage;
//
//	@JsonProperty(value = "Total")//小说列表总条数
//	private BigDecimal total;

    @JsonProperty(value = "TotalPage")//小说总页数
    private BigDecimal totalPage;
}
