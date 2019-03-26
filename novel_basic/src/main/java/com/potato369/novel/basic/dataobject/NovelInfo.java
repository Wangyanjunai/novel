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
     * @serialField serialVersionUID: 序列号
     */
    private static final long serialVersionUID = -8639503100980373589L;

    /**
     * @serialField id：id，主键id
     */
    @Id
    @Column(name = "id", nullable = false, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @serialField fileName：小说名称
     */
    @Column(name = "file_name", length = 256)
    private String fileName;

    /**
     * @serialField cover：封面图片
     */
    @Column(name = "cover", length = 256)
    private String cover;

    /**
     * @serialField title：标题
     */
    @Column(name = "title", length = 256)
    private String title;

    /**
     * @serialField author：作者
     */
    @Column(name = "author", length = 256)
    private String author;

    /**
     * @serialField publisher：出版社
     */
    @Column(name = "publisher", length = 128)
    private String publisher;

    /**
     * @serialField bookId：文件id
     */
    @Column(name = "book_id", nullable = false, length = 256)
    private String bookId;

    /**
     * @serialField totalWords：总字数
     */
    @Column(name = "total_words", length = 16)
    private BigDecimal totalWords;

    /**
     * @serialField novelStatus：状态
     */
    @Column(name = "novel_status", length = 4)
    private Integer novelStatus;

    /**
     * @serialField categoryType：类目类型编号
     */
    @Column(name = "category_type", length = 4)
    private Integer categoryType;

    /**
     * @serialField categoryText：类目类型名称
     */
    @Column(name = "category_text", length = 128)
    private String categoryText;

    /**
     * @serialField lang：语言
     */
    @Column(name = "lang", length = 8)
    private String lang;

    /**
     * @serialField rootFile：小说opf文件路径
     */
    @Column(name = "root_file", length = 256)
    private String rootFile;

    /**
     * @serialField description：小说简介
     */
    @Column(name = "introduction", length = 1024)
    private String introduction;

    /**
     * @serialField selected：是否被选中（书架中应用到），0-未选中，1-已选中；默认“0”未选中
     */
    @Column(name = "selected", length = 4)
    private Integer selected;

    /**
     * @serialField author：是否为私密阅读，0-否，1-是；默认“0”未私阅
     */
    @Column(name = "privated", length = 4)
    private Integer privated;
    /**
     * @serialField cache：是否被缓存（书架中会应用到，使用IndexedDB数据库缓存电子书），0-未缓存，1-已缓存；默认“0”未缓存
     */
    @Column(name = "cache", length = 4)
    private Integer cache;

    /**
     * @serialField haveRead：是否已经阅读过，0-未阅读，1-已阅读；默认“0”未阅读
     */
    @Column(name = "have_read", length = 4)
    private Integer haveRead;

    /**
     * @serialField type：类型Type
     */
    @Column(name = "type", length = 4)
    private Integer type;

    /**
     * @serialField result：匹配结果
     */
    @Column(name = "result", length = 256)
    private String result;

    /**
     * @serialField percent：百分比
     */
    @Column(name = "percent", length = 64)
    private String percent;

    /**
     * @serialField readers：阅读人数；默认“0 - 阅读人数为零”
     */
    @Column(name = "readers", length = 16)
    private BigDecimal readers;

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

