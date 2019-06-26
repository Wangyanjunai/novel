package com.potato369.novel.app.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * @ClassName: ProductInfoVO
 * @Function:
 * @Reason: 买家商品数据封装类
 * @Date: 2019年6月10日 下午4:19:21
 * @Desc:前端充值或者兑换的商品信息
 * @author 王艳军
 * @version 1.0
 * @since JDK 1.6
 * <pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ProductVO {

    @JsonProperty(value = "DataList")//充值兑换信息数据列表
    private List<ProductInfoVO> list;

    @JsonProperty(value = "TotalPage")//充值兑换信息数据总页数
    private BigDecimal totalPage;
}
