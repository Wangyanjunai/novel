package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName HotWordsInfoVO
 * @Desc 热词搜索vo
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 11:19
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class HotWordsInfoVO {

    @JsonProperty(value = "word")
    private String word;

    @JsonProperty(value = "times")
    private BigDecimal times;

    @JsonProperty(value = "isNew")
    private Integer isNew;

    @JsonProperty(value = "soaring")
    private BigDecimal soaring;
}
