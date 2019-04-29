package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.potato369.novel.app.web.utils.serializer.Date2LongSerializer;
import lombok.Builder;
import lombok.Data;
import org.nustaq.serialization.annotations.Serialize;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

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
public class NovelChapterInfoVO {

    /**
     * <pre>
     * @jsonField serialVersionUID：序列号。
     * </pre>
     */
    private static final long serialVersionUID = -2729952944003350231L;

    /**
     * <pre>
     * @jsonField chapterId：章节id，主键。
     * </pre>
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * <pre>
     * @jsonField bookId：章节所属小说id。
     * </pre>
     */
    @JsonProperty(value = "novel_id")
    private String bookId;

    /**
     * <pre>
     * @jsonField chapterIndex：章节索引。
     * </pre>
     */
    @JsonProperty(value = "index")
    private Integer index;

    /**
     * <pre>
     * @jsonField title：章节标题（名称）。
     * </pre>
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * <pre>
     * @jsonField newestChapterTitle：最新章节标题（名称）。
     * </pre>
     */
    @JsonProperty(value = "newest_chapter_title")
    private String newestChapterTitle;

    /**
     * <pre>
     * @jsonField url：章节网页路径URL
     * </pre>
     */
    @JsonProperty(value = "url")
    private String url;

    /**
     * <pre>
     * @jsonField createTime：创建时间。
     * </pre>
     */
    @JsonProperty(value = "create_time")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * <pre>
     * @jsonField updateTime：更新时间。
     * </pre>
     */
    @JsonProperty(value = "update_time")
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;    
}
