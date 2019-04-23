/**
 * <pre>
 * Project Name: novel
 * File Name: NovelInfo.java
 * Package Name: com.potato369.novel.dataobject
 * Create Date: 2018年12月14日 下午12:40:53
 * Copyright (c) 2018, 版权所有 (C) 2016-2036 土豆互联科技(深圳)有限公司 www.potato369.com All Rights Reserved
 * </pre>
 */
package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName NovelInfo
 * @Desc 小说信息记录实体类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018年12月14日 下午12:40:53
 * @CreateBy Eclipse IDEA Neon.3 Release(4.6.3)
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技（深圳）有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "NovelInfo")
@NoArgsConstructor
@Table(name = "novel_info")
public class NovelInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID: 序列号。
     * </pre>
     */
    private static final long serialVersionUID = -8639503100980373589L;

    /**
     * <pre>
     * @serialField id：id，主键id。
     * </pre>
     */
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    /**
     * <pre>
     * @serialField coverURL：封面图片路径。
     * </pre>
     */
    @Column(name = "cover_url", nullable = true, length = 1024)
    private String coverURL;

    /**
     * <pre>
     * @serialField title：小说名标题（名称）。
     * </pre>
     */
    @Column(name = "title", nullable = true, length = 256)
    private String title;

    /**
     * <pre>
     * @serialField author：作者。
     * </pre>
     */
    @Column(name = "author", nullable = true, length = 256)
    private String author;

    /**
     * <pre>
     * @serialField publisher：出版社，或者爬取的网站名称。
     * </pre>
     */
    @Column(name = "publisher", nullable = true, length = 128)
    private String publisher;

    /**
     * <pre>
     * @serialField totalWords：总字数。
     * </pre>
     */
    @Column(name = "total_words", nullable = true, length = 16)
    private BigDecimal totalWords;

    /**
     * @serialField novelStatus：状态
     */
    @Column(name = "novel_status", nullable = true, length = 4)
    private Integer novelStatus;

    /**
     * @serialField categoryType：类目类型编号
     */
    @Column(name = "category_type", nullable = false, length = 4)
    private Integer categoryType;

    /**
     * @serialField categoryCNText：类目类型中文名称
     */
    @Column(name = "category_cn_text", nullable = false, length = 64)
    private String categoryCNText;
    
    /**
     * @serialField categoryEnText：类目类型英文名称
     */
    @Column(name = "category_en_text", nullable = false, length = 64)
    private String categoryENText;

    /**
     * @serialField introduction：小说简介
     */
    @Column(name = "introduction", nullable = true, length = 4096)
    private String introduction;

    /**
     * @serialField readers：阅读（点击）用户数；默认“0-阅读（点击）用户数”
     */
    @Column(name = "readers", nullable = true, length = 16)
    private BigDecimal readers;
    
    /**
     * @serialField recentReaders：最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
     */
    @Column(name = "recent_readers", nullable = true, length = 16)
    private BigDecimal recentReaders;
    
    /**
     * @serialField clickNumber：点击次数
     */
    @Column(name = "click_number", nullable = true, length = 16)
    private BigDecimal clickNumber;
    
    /**
     * @serialField newestChapterId：最新章节id
     */
    @Column(name = "newest_chapter_id", nullable = true, length = 32)
    private String newestChapterId;
    
    /**
     * @serialField newestChapterTitle：最新章节标题（名称）
     */
    @Column(name = "newest_chapter_title", nullable = true, length = 512)
    private String newestChapterTitle;
    
    /**
     * @serialField totalChapters：总章节数
     */
    @Column(name = "total_chapters", nullable = true, length = 6)
    private Integer totalChapters;
    
    /**
     * @serialField retention：留存率，现在只是保存数字，显示的时候加上百分比
     */
    @Column(name = "retention", nullable = true, length = 3)
    private Integer retention;

    /**
     * @serialField createTime：创建时间
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * @serialField updateTime：更新时间
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;
}

