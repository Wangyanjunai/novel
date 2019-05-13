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
 * @ClassName RankVO
 * @Desc RankVO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/9 15:38
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class RankVO {

    /**
     * <pre>
     * 排行的名字
     * </pre>
     */
    @JsonProperty(value = "Title")
    private String title;

    /**
     * <pre>
     * 排行的名字
     * </pre>
     */
    @JsonProperty(value = "ShortTitle")
    private String shortTitle;

    /**
     * <pre>
     * 排行的数据
     * </pre>
     */
    @JsonProperty(value = "DataList")
    private List<NovelInfoVO> ranksData;
}
