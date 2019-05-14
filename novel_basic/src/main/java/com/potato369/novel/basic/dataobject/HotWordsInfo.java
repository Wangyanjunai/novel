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
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName HotWordsInfo
 * @Desc hot_words_info
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 10:38
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "HotWordsInfo")
@NoArgsConstructor
@Table(name = "hot_words_info")
public class HotWordsInfo implements Serializable {

    /**
     * <pre>
     * @serialField wordsId：搜索热词id，主键。
     * </pre>
     */
    @Id
    @Column(name = "word_id", nullable = false, length = 32)
    private String wordId;

    /**
     * <pre>
     * @serialField words：搜索热词。
     * </pre>
     */
    @Column(name = "word", nullable = false, length = 256)
    private String word;

    /**
     * <pre>
     * @serialField times：搜索次数。
     * </pre>
     */
    @Column(name = "times", nullable = false, length = 16)
    private BigDecimal times;

    /**
     * <pre>
     * @serialField isNew：是否是新创建的，0-新创建，1-已经有的。
     * </pre>
     */
    @Column(name = "is_new", nullable = false, length = 4)
    private Integer isNew;

    /**
     * <pre>
     * @serialField soaring：增长值。
     * </pre>
     */
    @Column(name = "soaring", nullable = false, length = 16)
    private BigDecimal soaring;

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
