package com.potato369.novel.basic.dataobject;

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
public class NovelAdvertisement {

	@Id
    @Column(name = "ad_id", nullable = false, length = 32)
	private String adId;
	
	@Column(name = "tag", nullable = false, length = 32)
	private Integer tag;
	
	@Column(name = "image_url", nullable = false, length = 256)
	private String imageUrl;
	
	@Column(name = "link_url", nullable = true, length = 256)
	private String linkUrl;
	
	@Column(name = "create_time", nullable = false, length = 0)
	private Date createTime;
	
	@Column(name = "update_time", nullable = false, length = 0)
	private Date updateTime;
}
