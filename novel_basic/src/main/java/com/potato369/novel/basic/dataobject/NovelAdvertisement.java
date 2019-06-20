package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.NovelAdvertisementTag1Enum;
import com.potato369.novel.basic.enums.NovelAdvertisementTag2Enum;
import com.potato369.novel.basic.utils.EnumUtil;
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
 * 类名: NovelAdvertisement
 * 类的作用:存储加载的广告信息数据实体类
 * 创建原因:小说加载广告信息数据记录表
 * 创建时间: 2019年4月18日 上午11:15:18
 * 描述: 小说加载广告信息数据记录表
 * @author Jack
 * @version
 * @since JDK 1.6
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "NovelAdvertisement")
@NoArgsConstructor
@Table(name = "novel_advertisement")
public class NovelAdvertisement implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    @Transient
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
     * @serialField tag1：标识广告是应用类广告还是初始页面跳转广告，0-跳转广告，1-应用内广告，默认：0-跳转广告。
     * </pre>
     */
    @Builder.Default
    @Column(name = "tag1", nullable = false, length = 1)
    private Integer tag1 = NovelAdvertisementTag1Enum.SKIP_AD.getCode();

    /**
     * <pre>
     * @serialField tag2：标识广告是否可以跳转，0-可以跳转，1-不可以跳转。默认：0-可以跳转。
     * </pre>
     */
    @Builder.Default
    @Column(name = "tag2", nullable = false, length = 1)
    private Integer tag2 = NovelAdvertisementTag2Enum.JUMP.getCode();

    /**
     * <pre>
     * @serialField imageUrl：广告图片链接绝对路径URL。
     * </pre>
     */
    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    /**
     * <pre>
     * @serialField linkUrl：广告跳转链接绝对路径URL。
     * </pre>
     */
    @Column(name = "link_url", length = 1024)
    private String linkUrl;

    /**
     * <pre>
     * @serialField novelId：小说id。
     * </pre>
     */
    @Column(name = "novel_id", length = 32)
    private String novelId;

    /**
     * <pre>
     * @serialField novelParentCategoryId：小说分类所属父级id。
     * </pre>
     */
    @Column(name = "novel_parent_category_id", length = 32)
    private String novelParentCategoryId;

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
    public NovelAdvertisementTag1Enum getNovelAdvertisementTag1Enum() {
        return EnumUtil.getByCode(tag1, NovelAdvertisementTag1Enum.class);
    }

    @Transient
    public NovelAdvertisementTag2Enum getNovelAdvertisementTag2Enum() {
        return EnumUtil.getByCode(tag2, NovelAdvertisementTag2Enum.class);
    }
}
