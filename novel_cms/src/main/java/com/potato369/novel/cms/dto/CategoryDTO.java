package com.potato369.novel.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.cms.dto
 * @ClassName CategoryDTO
 * @Desc 类目信息DTO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/3/29 15:47
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CategoryDTO {

    /**
     * <pre>
     * @Field categoryId：类目id
     * </pre>
     */
    private String categoryId;

    /**
     * <pre>
     * @Field categoryId1：类目id1
     * </pre>
     */
    private String categoryId1;

    /**
     * <pre>
     * @Field parentCategoryId：父级类目id
     * </pre>
     */
    private String parentCategoryId;

    /**
     * <pre>
     * @Field parentCategoryName：父级类目名称
     * </pre>
     */
    private String parentCategoryName;

    /**
     * <pre>
     * @Field categoryName：类目名称
     * </pre>
     */
    private String categoryName;

    /**
     * <pre>
     * @Field categoryName：类目英文名称
     * </pre>
     */
    private String categoryEnName;

    /**
     * <pre>
     * @Field categoryType：类目类型
     * </pre>
     */
    private Integer categoryType;

    /**
     * <pre>
     * @Field isDeleted：类目是否删除
     * </pre>
     */
    private Integer isDeleted;

    /**
     * <pre>
     * @Field createTime：创建时间
     * </pre>
     */
    private Date createTime;

    /**
     * <pre>
     * @Field updateTime：更新时间
     * </pre>
     */
    private Date updateTime;
}
