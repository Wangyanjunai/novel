package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.HotWordsIsNewEnum;
import com.potato369.novel.basic.utils.EnumUtil;
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
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName HotWordsInfo
 * @Desc 搜索热词信息记录实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 10:38
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "HotWordsInfo")
@NoArgsConstructor
@Table(name = "hot_words_info")
public class HotWordsInfo implements Serializable {

    /**
     * <pre>
     * serialVersionUID：序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = -5083836642166628301L;

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
    @Builder.Default
    @Column(name = "times", nullable = false, length = 16)
    private BigDecimal times = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField isNew：是否新建，0-新建；1-已有，默认：0-新建。
     * </pre>
     */
    @Builder.Default
    @Column(name = "is_new", nullable = false, length = 1)
    private Integer isNew = HotWordsIsNewEnum.NEW.getCode();

    /**
     * <pre>
     * @serialField soaring：增长值，默认：0。
     * </pre>
     */
    @Builder.Default
    @Column(name = "soaring", length = 16)
    private BigDecimal soaring = BigDecimal.ZERO;

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

    @Transient
    public HotWordsIsNewEnum getHotWordsIsNewEnum() {
        return EnumUtil.getByCode(isNew, HotWordsIsNewEnum.class);
    }
}
