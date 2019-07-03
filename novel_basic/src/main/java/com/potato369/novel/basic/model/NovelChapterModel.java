package com.potato369.novel.basic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.model
 * @ClassName NovelChapterModel
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/7/3 16:18
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NovelChapterModel implements Serializable {

    private String id;

    private Integer index;

    private String title;

    private Date createTime;
}
