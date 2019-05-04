package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName NovelChapterInfoVO
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/29 16:15
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
@Builder
public class NovelChapterTitleAndContentVO {

    /**
     * <pre>
     * @jsonField title：章节标题（名称）。
     * </pre>
     */
    @JsonProperty(value = "title")
    private String title;
    
    /**
     * <pre>
     * @jsonField content：章节内容。
     * </pre>
     */
    @JsonProperty(value = "content")
    private String content;
   
}