package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName CategoryBookVO
 * @Desc RequestParam
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/9 16:19
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Builder
@Data
public class CategoryBookVO {

    @JsonProperty(value = "DataList")
    private List<NovelInfoVO> books;

    @JsonProperty(value = "TotalPage")
    private BigDecimal totalPage;

}
