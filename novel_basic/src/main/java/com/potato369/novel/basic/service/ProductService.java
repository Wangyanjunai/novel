package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName ProductService
 * @Desc 商品业务Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:30
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface ProductService {
    /**
     * 根据商品id查询商品信息
     * @param productInfoId
     * @return
     */
	@Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    ProductInfo findOne(String productInfoId) throws Exception;

    /**
     * 查询所有的商品信息
     * @return
     */
	@Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<ProductInfo> findAll() throws Exception;
	
    /**
     * 根据产品计算类型查询所有的商品信息列表
     * @return
     */
	@Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	List<ProductInfo> findAllByType(Integer type);

    /**
     * 分页查询所有的商品信息
     * @param pageable
     * @return
     */
	@Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<ProductInfo> findAll(Pageable pageable) throws Exception;
}
