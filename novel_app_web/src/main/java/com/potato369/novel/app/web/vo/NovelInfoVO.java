package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName NovelInfoVO
 * @Desc statistics
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/29 11:56
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NovelInfoVO {

    /**
     * <pre>
     * @jsonField id：id，主键id。
     * </pre>
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * <pre>
     * @jsonField coverURL：封面图片路径。
     * </pre>
     */
    @JsonProperty(value = "cover")
    private String coverURL;

    /**
     * <pre>
     * @jsonField title：小说名标题（名称）。
     * </pre>
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * <pre>
     * @jsonField author：作者。
     * </pre>
     */
    @JsonProperty(value = "author")
    private String author;

    /**
     * <pre>
     * @jsonField totalWords：总字数。
     * </pre>
     */
    @JsonProperty(value = "totalWords")
    private BigDecimal totalWords;

    /**
     * @jsonField novelStatus：状态
     */
    @JsonProperty(value = "status")
    private Integer novelStatus;

    /**
     * @jsonField categoryType：类目类型编号
     */
    @JsonProperty(value = "type")
    private Integer categoryType;

    /**
     * @jsonField categoryCNText：类目类型中文名称
     */
    @JsonProperty(value = "cn_text")
    private String categoryCNText;

    /**
     * @jsonField introduction：小说简介
     */
    @JsonProperty(value = "desc")
    private String introduction;

    /**
     * @jsonField readers：阅读（点击）用户数；默认“0-阅读（点击）用户数”
     */
    @JsonProperty(value = "readers")
    private BigDecimal readers;

    /**
     * @jsonField recentReaders：最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
     */
    @JsonProperty(value = "recent_readers")
    private BigDecimal recentReaders;

    /**
     * @jsonField clickNumber：点击次数
     */
    @JsonProperty(value = "clicks")
    private BigDecimal clickNumber;

    /**
     * @jsonField newestChapterId：最新章节id
     */
    @JsonProperty(value = "newest_chapter_id")
    private String newestChapterId;

    /**
     * @jsonField newestChapterTitle：最新章节标题（名称）
     */
    @JsonProperty(value = "newest_chapter_title")
    private String newestChapterTitle;

    /**
     * @jsonField totalChapters：总章节数
     */
    @JsonProperty(value = "total_chapters")
    private Integer totalChapters;

    /**
     * @jsonField retention：留存率，现在只是保存数字，显示的时候加上百分比
     */
    @JsonProperty(value = "retention")
    private Integer retention;

    /**
     * @jsonField createTime：创建时间
     */
    @JsonProperty(value = "create_time")
    private Date createTime;

    /**
     * @jsonField updateTime：更新时间
     */
    @JsonProperty(value = "update_time")
    private Date updateTime;
}
