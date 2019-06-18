package com.potato369.novel.app.web.converter;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import com.potato369.novel.app.web.vo.ProductInfoVO;
import com.potato369.novel.basic.dataobject.ProductInfo;

/**
 * <pre>
 * @ClassName ProductInfo2ProductInfoVOConverter
 * @Function 将商品信息转换为商品数据封装信息转换器
 * @Reason 将商品信息转换为商品数据封装信息转换器
 * @Date 2019年6月10日 下午4:52:09
 * @Desc 将商品信息转换为商品数据封装信息转换器
 * @author 王艳军
 * @version 1.0
 * @since JDK 1.6
 * <pre>
 */
public class ProductInfo2ProductInfoVOConverter {

	public static ProductInfoVO convert(ProductInfo productInfo) {
		ProductInfoVO productInfoVO = ProductInfoVO.builder().build();
		BeanUtils.copyProperties(productInfo, productInfoVO);
//		if (ProductCalculateTypeEnum.DAY.getCode().equals(productInfo.getProductType())) {
//			productInfoVO.setTypeName(ProductCalculateTypeEnum.DAY.getMessage());
//		}
//		if (ProductCalculateTypeEnum.MONTH.getCode().equals(productInfo.getProductType())) {
//			productInfoVO.setTypeName(ProductCalculateTypeEnum.MONTH.getMessage());
//		}
		if (null == productInfo.getDateValue()) {
			productInfoVO.setDateValue(productInfo.getProductAmount().intValue());
		}
		return productInfoVO;
	}
	
	public static List<ProductInfoVO> convertToProductInfoVOList(List<ProductInfo> productInfoList) {
		return productInfoList.stream().map(productInfo -> convert(productInfo)).collect(Collectors.toList());
	}
}
