package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName ShelfInfoVO
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 17:53
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ShelfDetailInfoVO {
    /**
     * <pre>
     * @serialField shelfId：联合主键，小说id。
     * </pre>
     */
    @JsonProperty(value = "novel")
    private NovelInfoVO novel;

    /**
     * <pre>
     * @serialField lastReadChapterIndex：最后一次阅读的章节索引index。
     * </pre>
     */
    @JsonProperty(value = "lastReadChapterIndex")
    private Integer lastReadChapterIndex;

    /**
     * <pre>
     * @serialField lastReadPage：最后一次阅读章节的页码。
     * </pre>
     */
    @JsonProperty(value = "lastReadPage")
    private Integer lastReadPage;

    /**
     * <pre>
     * @serialField hasUpdate：书架的这本小说是否有新的章节更新，0-无更新，1-有更新，默认0。
     * </pre>
     */
    @JsonProperty(value = "hasUpdate")
    private Integer hasUpdate;

    /**
     * <pre>
     * @serialField sort：保存自定义排序的顺序。
     * </pre>
     */
    @JsonProperty(value = "sort")
    private Integer sort;

    /**
     * <pre>
     * @serialField isOrNotTop：书架的这本小说是否开启置顶，0-不开启，1-开启，默认0。
     * </pre>
     */
    @JsonProperty(value = "isOrNotTop")
    private Integer isOrNotTop;

    /**
     * <pre>
     * @serialField isOrNotPush：书架的这本小说是否开启小说章节更新消息推送，0-不开启，1-开启，默认0。
     * </pre>
     */
    @JsonProperty(value = "isOrNotPush")
    private Integer isOrNotPush;

    /**
     * <pre>
     * @serialField lastChapterUpdateTime：最新章节更新时间。
     * </pre>
     */
    @JsonProperty(value = "lastChapterUpdateTime")
    private Date lastChapterUpdateTime;

    /**
     * <pre>
     * @serialField latestReadTime：最后一次阅读时间。
     * </pre>
     */
    @JsonProperty(value = "lastReadTime")
    private Date lastReadTime;

    /**
     * <pre>
     * @serialField createTime：创建时间。
     * </pre>
     */
    @JsonProperty(value = "createTime")
    private Date createTime;

    /**
     * <pre>
     * @serialField updateTime：更新时间。
     * </pre>
     */
    @JsonProperty(value = "updateTime")
    private Date updateTime;
}
