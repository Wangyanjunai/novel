package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.repository.ProductInfoRepository;
import com.potato369.novel.basic.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName ProductServiceImpl
 * @Desc 书币产品信息业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:34
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    /**
     * 根据书币产品id查询书币产品信息
     *
     * @param productInfoId
     * @return
     */
    @Override
    public ProductInfo findOne(String productInfoId) throws Exception{
        return repository.findOne(productInfoId);
    }

    /**
     * 查询所有的书币产品信息
     *
     * @return
     */
    @Override
    public List<ProductInfo> findAll() throws Exception{
        return repository.findAll();
    }

    /**
     * 分页查询所有的书币产品信息
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) throws Exception{
        return repository.findAll(pageable);
    }

	/**
	 * <pre>
	 * 描述该方法的实现功能： 根据产品计算类型查询所有的商品信息列表
	 * @see com.potato369.novel.basic.service.ProductService#findAllByType(java.lang.Integer)
	 * </pre>
	 */
	@Override
	public List<ProductInfo> findAllByType(Integer type) {
		return repository.findByProductCalculateType(type);
	}
}
