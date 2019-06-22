package com.potato369.novel.basic.dataobject;

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
 * @ClassName NovelMenuInfo
 * @Desc 公众号自定义菜单按钮数据信息数据库对象实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/25 16:58
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "NovelMenuInfo")
@NoArgsConstructor
@Table(name = "novel_menu_info")
public class NovelMenuInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID: 序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = -8636503100980376529L;

    /**
     * <pre>
     * @serialField menuId：公众号自定义菜单id，主键。
     * </pre>
     */
    @Id
    @Column(name = "menu_id", nullable = false, length = 32)
    private String menuId;

    /**
     * <pre>
     * @serialField parentMenuId：公众号自定义菜单父级id。
     * </pre>
     */
    @Column(name = "parent_menu_id", length = 32)
    private String parentMenuId;

    /**
     * <pre>
     * @serialField type：菜单的响应动作类型，
     *      view表示网页类型；
     *      click表示点击类型；
     *      miniprogram表示小程序类型。
     * </pre>
     */
    @Column(name = "type", nullable = false, length = 64)
    private String type;

    /**
     * <pre>
     * @serialField name：菜单标题，不超过16个字节，子菜单不超过60个字节。
     * </pre>
     */
    @Column(name = "name", nullable = false, length = 64)
    private String name;

    /**
     * <pre>
     * @serialField key：菜单KEY值，用于消息接口推送，不超过128字节，click等点击类型必须。
     * </pre>
     */
    @Column(name = "key", length = 64)
    private String key;

    /**
     * <pre>
     * @serialField url：网页链接，
     * 用户点击菜单可打开链接，不超过1024字节。
     * type为miniprogram时，不支持小程序的老版本客户端将打开本url。
     * view、miniprogram类型必须
     * </pre>
     */
    @Column(name = "url", length = 256)
    private String url;

    /**
     * <pre>
     * @serialField readingNumber：阅读（点击）用户数，默认：0。
     * </pre>
     */
    @Builder.Default
    @Column(name = "reading_number", length = 16)
    private BigDecimal readingNumber = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField clickNumber：点击次数，默认：0。
     * </pre>
     */
    @Builder.Default
    @Column(name = "click_number", length = 16)
    private BigDecimal clickNumber = BigDecimal.ZERO;

    /**
     * <pre>
     * @serialField mediaId：调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型必须。
     * </pre>
     */
    @Column(name = "media_id", length = 128)
    private String mediaId;

    /**
     * <pre>
     * @serialField appId：小程序的appid，
     *      		miniprogram类型必须。
     * </pre>
     */
    @Column(name = "app_id", length = 64)
    private String appId;

    /**
     * <pre>
     * @serialField pagePath：小程序的页面路径，
     *              miniprogram 类型必须。
     * </pre>
     */
    @Column(name = "page_path", length = 256)
    private String pagePath;

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
