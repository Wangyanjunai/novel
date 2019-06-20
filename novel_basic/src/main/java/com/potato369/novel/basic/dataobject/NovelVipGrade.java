package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName NovelVipGrade
 * @Desc VIP等级信息记录实体。
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 10:50
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "NovelVipGrade")
@NoArgsConstructor
@Table(name = "novel_vip_grade")
public class NovelVipGrade implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = -8639503100982863589L;

    /**
     * <pre>
     * @serialField gradeId：主键，VIP等级id。
     * </pre>
     */
    @Id
    @Column(name = "grade_id", nullable = false, length = 32)
    private String gradeId;

    /**
     * <pre>
     * @serialField gradeName：VIP等级名称。
     * </pre>
     */
    @Column(name = "grade_name", length = 10)
    private String gradeName;

    /**
     * <pre>
     * @serialField gradeIntro：VIP等级简介。
     * </pre>
     */
    @Column(name = "grade_intro", length = 1024)
    private String gradeIntro;

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
