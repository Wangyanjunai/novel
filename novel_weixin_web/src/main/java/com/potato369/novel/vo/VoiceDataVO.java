package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName VoiceDataVO
 * @Desc 科大讯飞语音合成信息VO
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2018/12/24 20:48
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class VoiceDataVO implements Serializable {

	private static final long serialVersionUID = -2023228507972745298L;

	@JsonProperty(value = "err_no")
    private Integer err_no;

    @JsonProperty(value = "msg")
    private String msg;

    @JsonProperty(value = "path")
    private String path;
}
