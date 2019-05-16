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
     * 用户id
     */
    private String userId;

    /**
     * 书架id
     */
    private String shelfId;

    /**
     * 小说id
     */
    private String novelId;

    /**
     * 已经阅读到的章节id
     */
    private String chapterId;

    /**
     * 已经阅读到的章节索引
     */
    private Integer chapterIndex;
}
