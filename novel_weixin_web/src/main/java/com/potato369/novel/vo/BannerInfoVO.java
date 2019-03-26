package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName BannerInfoVO
 * @Desc 图片Banner
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/26 15:27
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class BannerInfoVO implements Serializable {

	private static final long serialVersionUID = 3304035949055757945L;

	@JsonProperty(value = "id")
    private String bannerId;

    @JsonProperty(value = "image")
    private String bannerImg;

    @JsonProperty(value = "infoId")
    private String novelInfoId;

    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "categoryText")
    private String category;

    @JsonProperty(value = "fileName")
    private String novelId;
}
