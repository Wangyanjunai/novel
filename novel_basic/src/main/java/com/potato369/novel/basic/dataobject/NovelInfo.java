/**
 * <pre>
 * Project Name: novel
 * File Name: NovelInfo.java
 * Package Name: com.potato369.novel.dataobject
 * Create Date: 2018年12月14日 下午12:40:53
 * Copyright (c) 2018, 版权所有 (C) 2016-2036 土豆互联科技(深圳)有限公司 www.potato369.com All Rights Reserved
 * </pre>
 */
package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.CategoryTypeEnum;
import com.potato369.novel.basic.enums.NovelInfoStatusEnum;
import com.potato369.novel.basic.utils.EnumUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName NovelInfo
 * @Desc 小说信息记录实体类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018年12月14日 下午12:40:53
 * @CreateBy Eclipse IDEA Neon.3 Release(4.6.3)
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技（深圳）有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "NovelInfo")
@NoArgsConstructor
@Table(name = "novel_info")
public class NovelInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
	@Transient
    private static final long serialVersionUID = -8639503100980373589L;

    /**
     * <pre>
     * @serialField id：id，主键。
     * </pre>
     */
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    /**
     * <pre>
     * @serialField coverURL：封面图片绝对路径url。
     * </pre>
     */
    @Column(name = "cover_url", length = 1024)
    private String coverURL;

    /**
     * <pre>
     * @serialField dataURL：小说信息数据的地址url。
     * </pre>
     */
    @Column(name = "data_url", length = 1024)
    private String dataURL;

    /**
     * <pre>
     * @serialField title：标题，小说的名称。
     * </pre>
     */
    @Column(name = "title", length = 256)
    private String title;

    /**
     * <pre>
     * @serialField author：作者。
     * </pre>
     */
    @Column(name = "author", length = 256)
    private String author;

    /**
     * <pre>
     * @serialField publisher：出版社或爬取的网站名称。
     * </pre>
     */
    @Column(name = "publisher", length = 128)
    private String publisher;

    /**
     * <pre>
     * @serialField totalWords：总字数。
     * </pre>
     */
    @Builder.Default
    @Column(name = "total_words", length = 16)
    private BigDecimal totalWords = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField novelStatus：状态，0-已完结；1-连载中，默认：1-连载中。
     * </pre>
     */
    @Builder.Default
    @Column(name = "novel_status", length = 1)
    private Integer novelStatus = NovelInfoStatusEnum.NOVEL_STATUS_UPDATING.getCode();

    /**
     * <pre>
     * @serialField categoryType：类型编号。
     * </pre>
     */
    @Column(name = "category_type", nullable = false, length = 4)
    private Integer categoryType;

    /**
     * <pre>
     * @serialField categoryCNText：中文名称。
     * </pre>
     */
    @Column(name = "category_cn_text", length = 64)
    private String categoryCNText;
    
    /**
     * <pre>
     * @serialField categoryEnText：英文名称。
     * <pre>
     */
    @Column(name = "category_en_text", length = 64)
    private String categoryENText;

    /**
     * <pre>
     * @serialField introduction：小说简介。
     * </pre>
     */
    @Column(name = "introduction")
    private String introduction;

    /**
     * <pre>
     * @serialField readers：阅读（点击）用户数；默认“0-阅读（点击）用户数”。
     * </pre>
     */
    @Builder.Default
    @Column(name = "readers", length = 16)
    private BigDecimal readers = BigDecimal.ZERO;
    
    /**
     * <pre>
     * @serialField recentReaders：最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”。
     * </pre>
     */
    @Builder.Default
    @Column(name = "recent_readers", length = 16)
    private BigDecimal recentReaders = BigDecimal.ZERO;
    
    /**
     * <pre>
     * @serialField clickNumber：点击次数。
     * </pre>
     */
    @Builder.Default
    @Column(name = "click_number", length = 16)
    private BigDecimal clickNumber = BigDecimal.ZERO;
    
    /**
     * <pre>
     * @serialField newestChapterId：最新章节id。
     * </pre>
     */
    @Column(name = "newest_chapter_id", length = 32)
    private String newestChapterId;
    
    /**
     * <pre>
     * @serialField newestChapterTitle：最新章节标题（名称）。
     * </pre>
     */
    @Column(name = "newest_chapter_title", length = 512)
    private String newestChapterTitle;
    
    /**
     * <pre>
     * @serialField totalChapters：章节总数。
     * </pre>
     */
    @Builder.Default
    @Column(name = "total_chapters", length = 6)
    private Integer totalChapters = Integer.valueOf(0);
    
    /**
     * <pre>
     * @serialField retention：留存率，现在只是保存数字，显示的时候加上百分比。
     * </pre>
     */
    @Builder.Default
    @Column(name = "retention", length = 3)
    private Integer retention = Integer.valueOf(0);

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

    public NovelInfo (String categoryCNText) {
        NovelInfo novelInfo = NovelInfo.builder().build();
        novelInfo.setId(UUIDUtil.gen32UUID());//1、设置小说id
        novelInfo.setCategoryCNText(categoryCNText);//2、设置小说分类中文名称
        compasByCategoryCNText(novelInfo, categoryCNText);
    }
    
    public NovelInfo compasByCategoryCNText(NovelInfo novelInfo, String categoryCNText) {
        if ("玄幻魔法".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.XUANHUANQIHUAN.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.XUANHUANQIHUAN.getMessage());//4、设置小说分类英文名称
        }
        if ("武侠修真".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.XIANXIAWUXIA.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.XIANXIAWUXIA.getMessage());//4、设置小说分类英文名称
        }
        if ("都市言情".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.DUSHIQINGGAN.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.DUSHIQINGGAN.getMessage());//4、设置小说分类英文名称
        }
        if ("历史穿越".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.CHUANGYUECHONGSHENG.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.CHUANGYUECHONGSHENG.getMessage());//4、设置小说分类英文名称
        }
        if ("恐怖悬疑".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.XUANYILINGYI.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.XUANYILINGYI.getMessage());//4、设置小说分类英文名称
        }
        if ("游戏竞技".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.YOUXIJINGJI.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.YOUXIJINGJI.getMessage());//4、设置小说分类英文名称
        }
        if ("军事科幻".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.LISHIJUNSHI.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.LISHIJUNSHI.getMessage());//4、设置小说分类英文名称
        }
        if ("女生频道".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.DUSHISHENGHUO.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.DUSHISHENGHUO.getMessage());//4、设置小说分类英文名称
        }
        if ("综合类型".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryTypeEnum.QITALEIBIE.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryTypeEnum.QITALEIBIE.getMessage());//4、设置小说分类英文名称
        }
        return novelInfo;
    }

    @Transient
    public NovelInfoStatusEnum getNovelInfoStatusEnum() {
        return EnumUtil.getByCode(novelStatus, NovelInfoStatusEnum.class);
    }
}

