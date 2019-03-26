package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName NovelInfoVO
 * @Desc 小说信息VO
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 19:26
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class NovelVO implements Serializable{
	
	private static final long serialVersionUID = 2767503728721847672L;

	@JsonProperty(value = "type")
    private Integer category;
    
    @JsonProperty(value = "cName")
    private String categoryName;
    
    @JsonProperty(value = "eName")
    private String categoryEnName;
    
    @JsonProperty(value = "list")
    private List<NovelInfoVO> novelInfoVOList;
    
    @JsonProperty(value = "total")
    private BigDecimal total;
}
