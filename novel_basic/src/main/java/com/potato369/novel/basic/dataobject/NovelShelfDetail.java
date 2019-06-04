package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.dataobject.idClass.NovelShelfDetailIdClass;
import com.potato369.novel.basic.enums.ShelfDetailEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName NovelShelf
 * @Desc novel_shelf 小说app书架详情信息记录实体类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 17:42
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "NovelShelfDetail")
@NoArgsConstructor
@Table(name = "novel_shelf_detail")
@IdClass(value = NovelShelfDetailIdClass.class)
public class NovelShelfDetail implements Serializable {

	/**
	 * <pre>
	 * serialVersionUID：序列号。
	 * </pre>
	 */
	@Transient
	private static final long serialVersionUID = -7780168205999475918L;

	/**
	 * <pre>
	 * @serialField shelfDetailId：书架详情id，联合主键。
	 * </pre>
	 */
	@Id
	@Column(name = "shelf_detail_id", nullable = false, length = 32)
	private String shelfDetailId;

	/**
	 * <pre>
	 * @serialField shelfId：书架id，联合主键。
	 * </pre>
	 */
	@Id
	@Column(name = "shelf_id", nullable = false, length = 32)
	private String shelfId;

	/**
	 * <pre>
	 * @serialField shelfId：小说id，联合主键。
	 * </pre>
	 */
	@Id
	@Column(name = "novel_id", nullable = false, length = 32)
	private String novelId;

	/**
	 * <pre>
	 * @serialField userId：用户mid，联合主键。
	 * </pre>
	 */
	@Id
	@Column(name = "user_id", nullable = false, length = 20)
	private String userId;

	/**
	 * <pre>
	 * @serialField lastReadChapterId：最后一次阅读的章节的id(可能为空)。
	 * </pre>
	 */
	@Column(name = "last_read_chapter_id", length = 32)
	private String lastReadChapterId;

	/**
	 * <pre>
	 * @serialField lastReadChapterIndex：最后一次阅读的章节索引index。
	 * </pre>
	 */
	@Column(name = "last_read_chapter_index", length = 6)
	private Integer lastReadChapterIndex;

	/**
	 * <pre>
	 * @serialField lastReadPage：最后一次阅读章节的页码。
	 * </pre>
	 */
	@Column(name = "last_read_page", length = 6)
	private Integer lastReadPage;

	/**
	 * <pre>
	 * @serialField hasUpdate：书架的这本小说是否有新的章节更新，0-无更新，1-有更新，默认0。
	 * </pre>
	 */
	@Builder.Default
	@Column(name = "has_update", length = 1)
	private Integer hasUpdate = ShelfDetailEnum.NO_UPDATE.getCode();

	/**
	 * <pre>
	 * @serialField sort：保存自定义排序的顺序。
	 * </pre>
	 */
	@Column(name = "sort", length = 6)
	private Integer sort;

	/**
	 * <pre>
	 * @serialField isOrNotTop：书架的这本小说是否开启置顶，0-开启，1-不开启，默认：0-不开启。
	 * </pre>
	 */
	@Builder.Default
	@Column(name = "is_or_not_top", length = 1)
	private Integer isOrNotTop = ShelfDetailEnum.NOT_TOP.getCode();

	/**
	 * <pre>
	 * @serialField isOrNotPush：书架的这本小说是否开启小说章节更新消息推送，0-不开启，1-开启，默认：0-不开启。
	 * </pre>
	 */
	@Builder.Default
	@Column(name = "is_or_not_push", length = 1)
	private Integer isOrNotPush = ShelfDetailEnum.NOT_PUSH.getCode();

	/**
	 * <pre>
	 * @serialField lastChapterUpdateTime：最新章节更新时间。
	 * </pre>
	 */
	@Column(name = "last_chapter_update_time", length = 64)
	private Date lastChapterUpdateTime;

	/**
	 * <pre>
	 * @serialField latestReadTime：最后一次阅读时间。
	 * </pre>
	 */
	@Column(name = "last_read_time", length = 64)
	private Date lastReadTime;

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
