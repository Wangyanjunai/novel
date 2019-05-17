package com.potato369.novel.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.dto
 * @ClassName ShelfInfoDTO
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-05-16 22:09
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ShelfInfoDTO {

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 最后一次的阅读时间
     */
    private String latestReadTime;

    /**
     * 最后一次阅读的章节index
     */
    private Integer latestReadSection;

    /**
     * 最后一次阅读的章节的id(可能为空)
     */
    private String latestReadSectionId;

    /**
     * 最后一次阅读章节的页码
     */
    private Integer latestReadPage;

    /**
     * 是否有新的章节更新，0-无更新，1-有更新
     */
    private Integer hasUpdate;

    /**
     * 保存自定义排序的顺序
     */
    private Integer sort;

    /**
     * 书架创建时间
     */
    private String createTime;

    /**
     * 书架更新时间
     */
    private String updateTime;

    /**
     *  最新章节更新时间
     */
    private String lastChapterUpdateTime;

    /**
     * 是否开启置顶，默认0-不开启，1-开启
     */
    private Integer isOrNotTop;

    /**
     * 是否开启更新消息推送，默认0-不开启，1-开启
     */
    private Integer isOrNotPush;
}
