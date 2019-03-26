package com.potato369.novel.basic.dataobject;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 类名: NovelChapter
 * 类的作用:存储小说对应的章节信息
 * 创建原因:存储小说对应的章节信息
 * 创建时间: 2019年3月26日 上午10:43:08
 * 描述:存储小说对应的章节信息
 * @author Jack
 * @version 0.0.1.SNAPSHOP
 * @since JDK 1.8
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "NovelChapter")
@NoArgsConstructor
@Table(name = "novel_chapter")
public class NovelChapter implements Serializable {
    /**
     * @serialField serialVersionUID: 序列号
     */
    private static final long serialVersionUID = -2729952944003350231L;

    /**
     * @serialField chapterId：章节id，主键
     */
    @Id
    @Column(name = "chapter_id", nullable = false, length = 32)
    private String chapterId;

    /**
     * @serialField chaperName：章节名称
     */
    @Column(name = "chapter_name", nullable = false, length = 256)
    private String chaperName;

    /**
     * @serialField bookId：章节所属小说id
     */
    @Column(name = "book_id", nullable = false, length = 11)
    private Integer bookId;

    /**
     * @serialField content：章节内容
     */
    @Column(name = "chapter_content", nullable = false)
    private String chapterContent;

    /**
     * @serialField chapterUrl：章节内容链接url
     */
    @Column(name = "chapter_url", nullable = false, length = 256)
    private String chapterUrl;

    /**
     * @serialField index：章节索引
     */
    @Column(name = "chapter_index", nullable = false, length = 8)
    private Integer chapterIndex;

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
