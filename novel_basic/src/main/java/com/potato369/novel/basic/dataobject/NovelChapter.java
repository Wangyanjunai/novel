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
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    private static final long serialVersionUID = -2729952944003350231L;

    /**
     * <pre>
     * @serialField chapterId：章节id，主键。
     * </pre>
     */
    @Id
    @Column(name = "chapter_id", nullable = false, length = 32)
    private String id;

    /**
     * <pre>
     * @serialField bookId：章节所属小说id。
     * </pre>
     */
    @Column(name = "book_id", nullable = false, length = 32)
    private String bookId;
    
    /**
     * <pre>
     * @serialField chapterIndex：章节索引。
     * </pre>
     */
    @Column(name = "chapter_index", nullable = true, length = 6)
    private Integer index;
    
    /**
     * <pre>
     * @serialField title：章节标题（名称）。
     * </pre>
     */
    @Column(name = "chapter_title", nullable = true, length = 512)
    private String title;
    
    /**
     * <pre>
     * @serialField newestChapterTitle：最新章节标题（名称）。
     * </pre>
     */
    @Column(name = "newest_chapter_title", nullable = true, length = 512)
    private String newestChapterTitle;
    
    /**
     * <pre>
     * @serialField url：章节网页路径URL
     * </pre>
     */
    @Column(name = "chapter_url", nullable = true, length = 1024)
    private String url;

    /**
     * <pre>
     * @serialField content：章节内容。
     * </pre>
     */
    @Column(name = "chapter_content", nullable = true)
    private String content;

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
