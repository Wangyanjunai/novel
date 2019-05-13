package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName NovelChapterVO
 * @Desc NovelChapter
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/8 16:32
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NovelChapterVO {

    @JsonProperty(value = "DataList")
    private List<NovelChapterInfoVO> novelChapterInfoVOList;

    @JsonProperty(value = "TotalPage")
    private Integer totalPage;
}
