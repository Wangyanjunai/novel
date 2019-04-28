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
 * 类名: NovelComment
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月28日 下午4:59:53
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@AllArgsConstructor
@Builder
@DynamicUpdate
@Data
@Entity(name = "NovelComment")
@NoArgsConstructor
@Table(name = "novel_comment")
public class NovelComment implements Serializable{
	
	/**
	 * <pre>
	 * @serialField serialVersionUID变量表示什么：序列号
	 * </pre>
	 */
	private static final long serialVersionUID = -3685893105639819618L;
	
	/**
	 * <pre>
	 * @serialField commentId：主键，评论id。
	 * </pre>
	 */
	@Id
	@Column(name = "comment_id", nullable = false, length = 32)
	private String commentId;

	/**
	 * <pre>
	 * @serialField userId：用户id。
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
	 * @serialField chapterId：不为空，则是对章节的评论的章节id，如果为空，则是对整本小说的评论，只是小说的id不为空。
	 * </pre>
	 */
	@Column(name = "chapter_id", nullable = true, length = 32)
	private String chapterId;
	
	/**
	 * <pre>
	 * @serialField commentTitle：评论标题。
	 * </pre>
	 */
	@Column(name = "comment_title", nullable = true, length = 512)
	private String commentTitle;
	
	/**
	 * <pre>
	 * @serialField commentContent：评论内容。
	 * </pre>
	 */
	@Column(name = "comment_content", nullable = true)
	private String commentContent;
	
	/**
	 * <pre>
	 * @serialField createTime：评论创建时间。
	 * </pre>
	 */
	@Column(name = "create_time", nullable = false, length = 64)
	private Date createTime;
	
	/**
	 * <pre>
	 * @serialField updateTime：评论更新时间。
	 * </pre>
	 */
	@Column(name = "update_time", nullable = false, length = 64)
	private Date updateTime;
}
