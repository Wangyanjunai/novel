package com.potato369.novel.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.form
 * @ClassName NovelInfoForm
 * @Desc 小说信息Form表单
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/23 15:39
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NovelInfoForm {

    /**
     * @Field id：id，主键id
     */
    private Integer id;

    /**
     * @Field fileName：小说文件名称
     */
    private String fileName;

    /**
     * @Field cover：封面图片路径
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
     * @Field type：类型Type
     */
    private Integer type;
}
