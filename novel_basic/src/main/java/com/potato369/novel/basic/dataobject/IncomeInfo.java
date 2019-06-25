package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
@Entity(name = "IncomeInfo")
@NoArgsConstructor
@Table(name = "income_info")
public class IncomeInfo implements Serializable {

    /**
     * <pre>
     * serialVersionUID：序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = -5083836642166628301L;

    /**
     * <pre>
     * @serialField incomeId：收益id，主键。
     * </pre>
     */
    @Id
    @Column(name = "income_id", nullable = false, length = 32)
    private String incomeId;

    /**
     * <pre>
     * @serialField userId：用户mid。
     * </pre>
     */
    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;
    
    /**
     * <pre>
     * @serialField imcomeAmount：收益金额（元）。
     * </pre>
     */
    @Default
    @Column(name = "income_amount", nullable = false, length = 10)
    private BigDecimal incomeAmount = BigDecimal.ZERO;


    /**
     * <pre>
     * @serialField incomeTime：收益获得时间。
     * </pre>
     */
    @Column(name = "income_time", nullable = false, length = 64)
    private Date incomeTime;
}
