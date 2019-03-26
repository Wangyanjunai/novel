package com.potato369.novel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.potato369.novel.dto.CategoryDTO;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName CategoryService
 * @Desc 小说类目信息业务Service接口
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 15:33
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface CategoryService {

	/**
	 * <pre>
	 * 根据类目类型typeid列表查询对应的类目类型
	 * @param categoryTypeList
	 * @return
	 * </pre>
	 */
	List<CategoryDTO> findByCategoryTypeIn(List<Integer> categoryTypeList);

	/**
	 * <pre>
	 * 保存类目类型对象
	 * @param categoryDTO
	 * @return
	 * </pre>
	 */
	CategoryDTO save(CategoryDTO categoryDTO);

	/**
	 * <pre>
	 * 根据主键categoryId删除类目信息
	 * @param categoryId
	 * </pre>
	 */
	CategoryDTO delete(String categoryId);

	/**
	 * <pre>
	 * 更新类目信息
	 * @param categoryDTO
	 * @return
	 * </pre>
	 */
	CategoryDTO update(CategoryDTO categoryDTO);

	/**
	 * <pre>
	 * 根据categoryId查询类目信息
	 * @param categoryId
	 * @return
	 * </pre>
	 */
	CategoryDTO findOne(String categoryId);

	/**
	 * <pre>
	 * 根据父级类目id查询类目信息
	 * @param parentCategoryId
	 * @return
	 * </pre>
	 */
	CategoryDTO findByParentCategoryId(String parentCategoryId);

	/**
	 * <pre>
	 * 根据类目类型text查询类目信息
	 * @param categoryEnName
	 * @return
	 * </pre>
	 */
	CategoryDTO findByCategoryEnName(String categoryEnName);

	/**
	 * <pre>
	 * 查找所有的类目信息
	 * @return
	 * </pre>
	 */
	List<CategoryDTO> findAll();

	/**
	 * <pre>
	 * 查找所有的父级类目id不为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	List<CategoryDTO> findAllParentCategoryIdIsNotNull();

	/**
	 * <pre>
	 * 查找所有的父级类目id为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	List<CategoryDTO> findAllParentCategoryIdIsNull();

	/**
	 * <pre>
	 * 分页查询所有的类目信息
	 * @param pageable
	 * @return
	 * </pre>
	 */
	Page<CategoryDTO> findAll(Pageable pageable);
}
