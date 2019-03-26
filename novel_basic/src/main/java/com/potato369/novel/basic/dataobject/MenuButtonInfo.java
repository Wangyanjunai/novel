package com.potato369.novel.basic.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @ClassName MenuButtonInfo
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
@DynamicUpdate
@Data
@Entity(name = "MenuButtonInfo")
@NoArgsConstructor
@Table(name = "menu_button_info")
public class MenuButtonInfo implements Serializable {

    /**
     * @serialField serialVersionUID: 序列号。
     */
    private static final long serialVersionUID = -8636503100980376529L;

    /**
     * <pre>
     * @serialField menuId：主键，公众号自定义菜单id。
     * </pre>
     */
    @Id
    @Column(name = "menu_id", nullable = false, length = 32)
    private String menuId;
    /**
     * <pre>
     * @serialField type：菜单的响应动作类型，
     *      view表示网页类型，
     *      click表示点击类型，
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
     * @serialField key：菜单KEY值，用于消息接口推送，不超过128字节，
     * click等点击类型必须。
     * </pre>
     */
    @Column(name = "key", nullable = true, length = 64)
    private String key;

    /**
     * <pre>
     * @serialField url：网页链接，
     * 用户点击菜单可打开链接，不超过1024字节。
     * type为miniprogram时，不支持小程序的老版本客户端将打开本url。
     * view、miniprogram类型必须
     * </pre>
     */
    @Column(name = "url", nullable = true, length = 256)
    private String url;

    /**
     * <pre>
     * @serialField mediaId：调用新增永久素材接口返回的合法media_id，media_id类型和view_limited类型必须
     * </pre>
     */
    @Column(name = "media_id", nullable = true, length = 128)
    private String mediaId;

    /**
     * <pre>
     * @serialField appId：小程序的appid，
     *      miniprogram类型必须
     * </pre>
     */
    @Column(name = "app_id", nullable = true, length = 64)
    private String appId;

    /**
     * <pre>
     * @serialField pagePath：小程序的页面路径，
     *          miniprogram类型必须
     * </pre>
     */
    @Column(name = "page_path", nullable = true, length = 256)
    private String pagePath;
}
