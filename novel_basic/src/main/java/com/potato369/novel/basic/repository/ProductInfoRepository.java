package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.ProductInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @InterfaceName ProductInfoRepository
 * @Desc 商品信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:27
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
	
	/**
	 * <pre>
	 * findByProductType方法的作用： 根据产品计算类型查询所有的商品信息列表
	 * @param productCalculateType
	 * @return List<ProductInfo>
	 * </pre>
	 */
	List<ProductInfo> findByProductCalculateType(Integer productCalculateType);
}
