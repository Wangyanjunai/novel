package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName NovelShelf
 * @Desc novel_shelf 小说app书架信息记录实体类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 17:42
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "NovelShelf")
@NoArgsConstructor
@Table(name = "novel_shelf")
@IdClass(com.potato369.novel.basic.dataobject.IdClass.class)
public class NovelShelf implements Serializable {

    /**
     * <pre>
     * @serialField menuId：主键，公众号自定义菜单id。
     * </pre>
     */
    @Column(name = "user_id", nullable = false, length = 32)
    private String userId;

    /**
     * <pre>
     * @serialField novelId：小说id。
     * </pre>
     */
    @Column(name = "novel_id", nullable = false, length = 32)
    private String novelId;

    /**
     * <pre>
     * @serialField chapterId：已经阅读到的章节id。
     * </pre>
     */
    @Column(name = "chapter_id", nullable = true, length = 32)
    private String chapterId;

    /**
     * <pre>
     * @serialField chapterIndex：已经阅读到的章节索引。
     * </pre>
     */
    @Column(name = "chapter_index", nullable = true, length = 6)
    private Integer chapterIndex;

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
