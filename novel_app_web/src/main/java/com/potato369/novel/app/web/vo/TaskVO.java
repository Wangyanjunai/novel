package com.potato369.novel.app.web.vo;

import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName TaskVO
 * @Desc 任务中心数据传输类实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/11 14:52
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TaskVO {
	
	/**
     * <pre>
     * @JsonProperty taskInfoVOList：任务信息数据列表。
     * </pre>
     */
	@JsonProperty(value = "DataList")
	private List<TaskInfoVO> taskInfoVOList;

    /**
     * <pre>
     * @JsonProperty totalPage：任务总页数。
     * </pre>
     */
	@JsonProperty(value = "TotalPage")
    private BigDecimal totalPage;
}
