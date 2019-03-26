package com.potato369.novel.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.form
 * @ClassName CategoryForm
 * @Desc 类目类型Form表单
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/18 17:58
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CategoryForm {

    /**
     * <pre>
     * @Filed categoryId：类目id
     * </pre>
     */
    private String categoryId;

    /**
     * <pre>
     * @Filed parentCategoryId：父级类目id
     * </pre>
     */
    private String parentCategoryId;

    /**
     * <pre>
     * @Filed categoryName：类目名称
     * </pre>
     */
    private String categoryName;

    /**
     * <pre>
     * @Filed categoryName：类目英文名称
     * </pre>
     */
    private String categoryEnName;

    /**
     * <pre>
     * @Filed categoryType：类目编号
     * </pre>
     */
    private Integer categoryType;

    /**
     * <pre>
     * @Filed isDeleted：此类目是否标记已删除，默认否
     * </pre>
     */
    private Integer isDeleted;
}
