package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName NovelCategory
 * @Desc 小说类目信息
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 15:19
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "NovelCategory")
@NoArgsConstructor
@Table(name = "novel_category")
public class NovelCategory implements Serializable {

    /**
     * @serialField serialVersionUID: 序列号
     */
    private static final long serialVersionUID = -8639503100980376589L;

    /**
     * @serialField categoryId：类目id，主键
     */
    @Id
    @Column(name = "category_id", nullable = false, length = 32)
    private String categoryId;

    /**
     * @serialField parentCategoryId：父级id
     */
    @Column(name = "parent_category_id", nullable = true, length = 32)
    private String parentCategoryId;

    /**
     * @serialField categoryName：类目名称
     */
    @Column(name = "category_name", nullable = true, length = 64)
    private String categoryName;

    /**
     * @serialField categoryEnName：类目英文名称
     */
    @Column(name = "category_en_name", nullable = true, length = 64)
    private String categoryEnName;

    /**
     * @serialField categoryType：类目类型编号
     */
    @Column(name = "category_type", nullable = true, length = 4)
    private Integer categoryType;

    /**
     * @serialField isDeleted：类目是否删除；0-否，1-是
     */
    @Column(name = "is_deleted", nullable = true, length = 4)
    private Integer isDeleted;

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
