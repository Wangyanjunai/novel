package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
public class ShelfInfoVO {

    @JsonProperty(value = "shelfId")
    private String shelfId;

    @JsonProperty(value = "userId")
    private String userId;

    @JsonProperty(value = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonProperty(value = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonProperty(value = "details")
    private List<ShelfDetailInfoVO> shelfDetailInfoVOList;
}
