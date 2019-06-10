package com.potato369.novel.app.web.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.potato369.novel.app.web.vo.ProductInfoVO;
import com.potato369.novel.basic.dataobject.ProductInfo;

/**
 * <pre>
 * 类名: ProductInfo2ProductInfoVO
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年6月10日 下午4:52:09
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */

public class ProductInfo2ProductInfoVOConverter {

	public static ProductInfoVO convert(ProductInfo productInfo) {
		ProductInfoVO productInfoVO = ProductInfoVO.builder().build();
		BeanUtils.copyProperties(productInfo, productInfoVO);
		return productInfoVO;
	}
	
	public static List<ProductInfoVO> convertToProductInfoVOList(List<ProductInfo> productInfoList) {
		return productInfoList.stream().map(productInfo -> convert(productInfo)).collect(Collectors.toList());
	}
}
