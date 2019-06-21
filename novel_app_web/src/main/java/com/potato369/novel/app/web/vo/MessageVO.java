package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName MessageVO
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/21 18:13
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class MessageVO {

    @JsonProperty(value = "DataList")
    private List<OrderMessageVO> messageVOList;

    @JsonProperty(value = "TotalPage")
    private BigDecimal totalPage;
}
