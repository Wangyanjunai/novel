package com.potato369.novel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.vo
 * @ClassName ResultVO
 * @Desc 返回数据结果VO
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 19:20
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Data
public class ResultVO<T> implements Serializable {

	private static final long serialVersionUID = -1221897281371574516L;

    @JsonProperty(value = "err_no")
	private Integer err_no;

    @JsonProperty(value = "msg")
    private String msg;

    @JsonProperty(value = "data")
    private T data;

    @JsonProperty(value = "total", defaultValue = "0")
    private BigDecimal total;
}
