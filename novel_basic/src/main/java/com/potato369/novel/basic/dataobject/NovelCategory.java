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
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Data
@DynamicUpdate
@Entity(name = "NovelCategory")
@NoArgsConstructor
@Table(name = "novel_category")
public class NovelCategory implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID: 序列号。
     * <pre>
     */
	@Transient
    private static final long serialVersionUID = -8639503100980376589L;

    /**
     * <pre>
     * @serialField categoryId：类目id，主键。
     * <pre>
     */
    @Id
    @Column(name = "category_id", nullable = false, length = 32)
    private String categoryId;

    /**
     * <pre>
     * @serialField parentCategoryId：父级id。
     * </pre>
     */
    @Column(name = "parent_category_id", nullable = true, length = 32)
    private String parentCategoryId;

    /**
     * <pre>
     * @serialField categoryCNText：类目中文名称。
     * <pre>
     */
    @Column(name = "category_cn_text", nullable = true, length = 64)
    private String categoryCNText;
    
    /**
     * <pre>
     * @serialField categoryENText：类目英文名称。
     * <pre>
     */
    @Column(name = "category_en_text", nullable = true, length = 64)
    private String categoryENText;
    
    /**
     * <pre>
     * @serialField categoryType：类目类型编号。
     * <pre>
     */
    @Column(name = "category_type", nullable = true, length = 4)
    private Integer categoryType;
    
    /**
     * <pre>
     * @serialField categoryUrl：类目路径url。
     * <pre>
     */
    @Transient
    private String categoryUrl;

    /**
     * <pre>
     * @serialField readingNumber：阅读（点击）用户数。
     * <pre>
     */
    @Column(name = "reading_number", nullable = true, length = 16)
    private BigDecimal readingNumber;

    /**
     * <pre>
     * @serialField clickNumber：点击次数。
     * <pre>
     */
    @Column(name = "click_number", nullable = true, length = 16)
    private BigDecimal clickNumber;

    /**
     * <pre>
     * @serialField isDeleted：类目是否删除；0-否，1-是。
     * </pre>
     */
    @Column(name = "is_deleted", nullable = true, length = 4)
    private Integer isDeleted;

    /**
     * <pre>
     * @serialField createTime：创建时间。
     * </pre>
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * <pre>
     * @serialField updateTime：更新时间。
     * </pre>
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;
}
