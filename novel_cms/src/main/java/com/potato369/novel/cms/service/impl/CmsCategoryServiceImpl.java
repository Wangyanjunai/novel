package com.potato369.novel.cms.service.impl;

import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.service.CategoryService;
import com.potato369.novel.cms.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.potato369.novel.cms.service.CmsCategoryService;

import java.util.List;

/**
 * <pre>
 * 类名: CategoryServiceImpl
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年3月29日 上午10:59:33
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Service
public class CmsCategoryServiceImpl implements CmsCategoryService {

	@Autowired
	private CategoryService categoryService;

	/**
	 * <pre>
	 * 根据类目类型typeid列表查询对应的类目类型
	 * @param categoryTypeList
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		List<NovelCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
		return null;
	}

	/**
	 * <pre>
	 * 保存类目类型对象
	 * @param categoryDTO
	 * @return
	 * </pre>
	 */
	@Override
	public CategoryDTO save(CategoryDTO categoryDTO) {
		return null;
	}

	/**
	 * <pre>
	 * 根据主键categoryId删除类目信息
	 * @param categoryId
	 * </pre>
	 */
	@Override
	public CategoryDTO delete(String categoryId) {
		return null;
	}

	/**
	 * <pre>
	 * 更新类目信息
	 * @param categoryDTO
	 * @return
	 * </pre>
	 */
	@Override
	public CategoryDTO update(CategoryDTO categoryDTO) {
		return null;
	}

	/**
	 * <pre>
	 * 根据categoryId查询类目信息
	 * @param categoryId
	 * @return
	 * </pre>
	 */
	@Override
	public CategoryDTO findOne(String categoryId) {
		return null;
	}

	/**
	 * <pre>
	 * 根据父级类目id查询类目信息
	 * @param parentCategoryId
	 * @return
	 * </pre>
	 */
	@Override
	public CategoryDTO findByParentCategoryId(String parentCategoryId) {
		return null;
	}

	/**
	 * <pre>
	 * 根据类目类型text查询类目信息
	 * @param categoryEnName
	 * @return
	 * </pre>
	 */
	@Override
	public CategoryDTO findByCategoryEnName(String categoryEnName) {
		return null;
	}

	/**
	 * <pre>
	 * 查找所有的类目信息
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findAll() {
		return null;
	}

	/**
	 * <pre>
	 * 查找所有的父级类目id不为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findAllParentCategoryIdIsNotNull() {
		return null;
	}

	/**
	 * <pre>
	 * 查找所有的父级类目id为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findAllParentCategoryIdIsNull() {
		return null;
	}

	/**
	 * <pre>
	 * 分页查询所有的类目信息
	 * @param pageable
	 * @return
	 * </pre>
	 */
	@Override
	public Page<CategoryDTO> findAll(Pageable pageable) {
		return null;
	}
}
