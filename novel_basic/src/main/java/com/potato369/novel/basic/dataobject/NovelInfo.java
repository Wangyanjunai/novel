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

import com.potato369.novel.basic.enums.CategoryEnum;
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
@DynamicUpdate
@Data
@Entity(name = "NovelInfo")
@NoArgsConstructor
@Table(name = "novel_info")
public class NovelInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID: 序列号。
     * </pre>
     */
    private static final long serialVersionUID = -8639503100980373589L;

    /**
     * <pre>
     * @serialField id：id，主键id。
     * </pre>
     */
    @Id
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    /**
     * <pre>
     * @serialField coverURL：封面图片路径。
     * </pre>
     */
    @Column(name = "cover_url", nullable = true, length = 1024)
    private String coverURL;

    /**
     * <pre>
     * @serialField title：小说名标题（名称）。
     * </pre>
     */
    @Column(name = "title", nullable = true, length = 256)
    private String title;

    /**
     * <pre>
     * @serialField author：作者。
     * </pre>
     */
    @Column(name = "author", nullable = true, length = 256)
    private String author;

    /**
     * <pre>
     * @serialField publisher：出版社，或者爬取的网站名称。
     * </pre>
     */
    @Column(name = "publisher", nullable = true, length = 128)
    private String publisher;

    /**
     * <pre>
     * @serialField totalWords：总字数。
     * </pre>
     */
    @Column(name = "total_words", nullable = true, length = 16)
    private BigDecimal totalWords;

    /**
     * @serialField novelStatus：状态
     */
    @Column(name = "novel_status", nullable = true, length = 4)
    private Integer novelStatus;

    /**
     * @serialField categoryType：类目类型编号
     */
    @Column(name = "category_type", nullable = false, length = 4)
    private Integer categoryType;

    /**
     * @serialField categoryCNText：类目类型中文名称
     */
    @Column(name = "category_cn_text", nullable = false, length = 64)
    private String categoryCNText;
    
    /**
     * @serialField categoryEnText：类目类型英文名称
     */
    @Column(name = "category_en_text", nullable = false, length = 64)
    private String categoryENText;

    /**
     * @serialField introduction：小说简介
     */
    @Column(name = "introduction", nullable = true, length = 4096)
    private String introduction;

    /**
     * @serialField readers：阅读（点击）用户数；默认“0-阅读（点击）用户数”
     */
    @Column(name = "readers", nullable = true, length = 16)
    private BigDecimal readers;
    
    /**
     * @serialField recentReaders：最近跟随阅读（点击）用户数；默认“0-最近跟随阅读（点击）用户数”
     */
    @Column(name = "recent_readers", nullable = true, length = 16)
    private BigDecimal recentReaders;
    
    /**
     * @serialField clickNumber：点击次数
     */
    @Column(name = "click_number", nullable = true, length = 16)
    private BigDecimal clickNumber;
    
    /**
     * @serialField newestChapterId：最新章节id
     */
    @Column(name = "newest_chapter_id", nullable = true, length = 32)
    private String newestChapterId;
    
    /**
     * @serialField newestChapterTitle：最新章节标题（名称）
     */
    @Column(name = "newest_chapter_title", nullable = true, length = 512)
    private String newestChapterTitle;
    
    /**
     * @serialField totalChapters：总章节数
     */
    @Column(name = "total_chapters", nullable = true, length = 6)
    private Integer totalChapters;
    
    /**
     * @serialField retention：留存率，现在只是保存数字，显示的时候加上百分比
     */
    @Column(name = "retention", nullable = true, length = 3)
    private Integer retention;

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

    public NovelInfo (String categoryCNText) {
        NovelInfo novelInfo = NovelInfo.builder().build();
        novelInfo.setId(UUIDUtil.gen32UUID());//1、设置小说id
        novelInfo.setCategoryCNText(categoryCNText);//2、设置小说分类中文名称
        if ("玄幻魔法".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.XUANHUANQIHUAN.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.XUANHUANQIHUAN.getMessage());//4、设置小说分类英文名称
        }
        if ("武侠修真".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.XIANXIAWUXIA.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.XIANXIAWUXIA.getMessage());//3、设置小说分类英文名称
        }
        if ("都市言情".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.DUSHIQINGGAN.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.DUSHIQINGGAN.getMessage());//4、设置小说分类英文名称
        }
        if ("历史穿越".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.CHUANGYUECHONGSHENG.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.CHUANGYUECHONGSHENG.getMessage());//4、设置小说分类英文名称
        }
        if ("恐怖悬疑".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.XUANYILINGYI.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.XUANYILINGYI.getMessage());//4、设置小说分类英文名称
        }
        if ("游戏竞技".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.YOUXIJINGJI.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.YOUXIJINGJI.getMessage());//4、设置小说分类英文名称
        }
        if ("军事科幻".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.LISHIJUNSHI.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.LISHIJUNSHI.getMessage());//4、设置小说分类英文名称
        }
        if ("女生频道".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.DUSHISHENGHUO.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.DUSHISHENGHUO.getMessage());//4、设置小说分类英文名称
        }
        if ("综合类型".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.QITALEIBIE.getCode());//3、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.QITALEIBIE.getMessage());//4、设置小说分类英文名称
        }
    }
    public NovelInfo compasByCategoryCNText(NovelInfo novelInfo, String categoryCNText) {
        if ("玄幻魔法".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.XUANHUANQIHUAN.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.XUANHUANQIHUAN.getMessage());//3、设置小说分类英文名称
        }
        if ("武侠修真".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.XIANXIAWUXIA.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.XIANXIAWUXIA.getMessage());//3、设置小说分类英文名称
        }
        if ("都市言情".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.DUSHIQINGGAN.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.DUSHIQINGGAN.getMessage());//3、设置小说分类英文名称
        }
        if ("历史穿越".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.CHUANGYUECHONGSHENG.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.CHUANGYUECHONGSHENG.getMessage());//3、设置小说分类英文名称
        }
        if ("恐怖悬疑".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.XUANYILINGYI.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.XUANYILINGYI.getMessage());//3、设置小说分类英文名称
        }
        if ("游戏竞技".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.YOUXIJINGJI.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.YOUXIJINGJI.getMessage());//3、设置小说分类英文名称
        }
        if ("军事科幻".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.LISHIJUNSHI.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.LISHIJUNSHI.getMessage());//3、设置小说分类英文名称
        }
        if ("女生频道".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.DUSHISHENGHUO.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.DUSHISHENGHUO.getMessage());//3、设置小说分类英文名称
        }
        if ("综合类型".equals(categoryCNText)) {
            novelInfo.setCategoryType(CategoryEnum.QITALEIBIE.getCode());//2、设置小说分类类型type
            novelInfo.setCategoryENText(CategoryEnum.QITALEIBIE.getMessage());//3、设置小说分类英文名称
        }
        return novelInfo;
    }
}

