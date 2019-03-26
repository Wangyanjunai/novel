package com.potato369.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.dto
 * @ClassName NovelInfoDTO
 * @Desc 小说信息DTO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-01-22 23:00
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NovelInfoDTO {

    /**
     * @Field id：主键id
     */
    private Integer id;

    /**
     * @Field fileName：文件名称
     */
    private String fileName;

    /**
     * @Field cover：封面图片
     */
    private String cover;

    /**
     * @Field title：标题
     */
    private String title;

    /**
     * @Field author：作者
     */
    private String author;

    /**
     * @Field publisher：出版社
     */
    private String publisher;

    /**
     * @Field bookId：文件id
     */
    private String bookId;

    /**
     * @Field totalWords：总字数
     */
    private BigDecimal totalWords;

    /**
     * @Field novelStatus：状态
     */
    private Integer novelStatus;

    /**
     * @Field categoryType：类目类型编号
     */
    private Integer categoryType;

    /**
     * @Field categoryText：类目类型名称
     */
    private String categoryText;

    /**
     * @Field lang：语言
     */
    private String lang;

    /**
     * @Field rootFile：小说opf文件路径
     */
    private String rootFile;

    /**
     * @Field description：小说简介
     */
    private String introduction;

    /**
     * @Field selected：是否被选中（书架中应用到），0-未选中，1-已选中；默认“0”未选中
     */
    private Integer selected;

    /**
     * @Field author：是否为私密阅读，0-否，1-是；默认“0”未私阅
     */
    private Integer privated;

    /**
     * @Field cache：是否被缓存（书架中会应用到，使用IndexedDB数据库缓存电子书），0-未缓存，1-已缓存；默认“0”未缓存
     */
    private Integer cache;

    /**
     * @Field haveRead：是否已经阅读过，0-未阅读，1-已阅读；默认“0”未阅读
     */
    private Integer haveRead;

    /**
     * @Field type：类型Type
     */
    private Integer type;

    /**
     * @Field result：匹配结果
     */
    private String result;

    /**
     * @Field percent：百分比
     */
    private String percent;

    /**
     * @Field readers：阅读人数；默认“0 - 阅读人数为零”
     */
    private BigDecimal readers;

    /**
     * @Field createTime：创建时间
     */
    private Date createTime;

    /**
     * @Field updateTime：更新时间
     */
    private Date updateTime;
}
