package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName RankInfoVO
 * @Desc RankInfoVO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/9 15:39
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class RankInfoVO {

    @JsonProperty(value = "_id")
    private String id;

    @JsonProperty(value = "title")
    private String title;
}
