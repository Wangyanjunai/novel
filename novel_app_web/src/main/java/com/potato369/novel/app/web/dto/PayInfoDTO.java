package com.potato369.novel.app.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.dto
 * @ClassName PayInfoDTO
 * @Desc 信息
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/13 16:29
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PayInfoDTO {

    @NotNull(message = "用户mid不能为空")
    @JsonProperty(value = "mid")
    private String userId;//用户mid

    @NotNull(message = "商品id不能为空")
    @JsonProperty(value = "pid")
    private String productId;//商品id

    @NotNull(message = "支付方式不能为空")
    @JsonProperty(value = "type")
    private Integer payType;//支付方式
}
