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
 * 类名: NovelAdvertisement
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月18日 上午11:15:18
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
@Entity(name = "NovelAdvertisement")
@NoArgsConstructor
@Table(name = "novel_advertisement")
public class NovelAdvertisement implements Serializable{

	/**
	 * <pre>
	 * @serialField serialVersionUID：序列号
	 * </pre>
	 */
	private static final long serialVersionUID = -7773745504940953409L;

	/**
	 * <pre>
	 * @serialField adId：主键，广告id。
	 * </pre>
	 */
	@Id
    @Column(name = "ad_id", nullable = false, length = 32)
	private String adId;
	
	/**
	 * <pre>
	 * @serialField tag：标识广告是否可以跳转，0-可以跳转，1-不可以跳转。
	 * </pre>
	 */
	@Column(name = "tag", nullable = false, length = 32)
	private Integer tag;
	
	/**
	 * <pre>
	 * @serialField imageUrl：广告图片链接地址。
	 * </pre>
	 */
	@Column(name = "image_url", nullable = false, length = 1256)
	private String imageUrl;
	
	/**
	 * <pre>
	 * @serialField linkUrl：广告跳转链接地址。
	 * </pre>
	 */
	@Column(name = "link_url", nullable = true, length = 1256)
	private String linkUrl;
	
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
