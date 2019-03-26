package com.potato369.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.dto
 * @ClassName CartDTO
 * @Desc 购物车DTO
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 18:46
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CartDTO {
    /**
     * <pre>
     * @Field productId：商品Id
     * </pre>
     */
    private String productId;

    /**
     * <pre>
     * @Field productQuantity：商品数量
     * </pre>
     */
    private Integer productQuantity;
}
