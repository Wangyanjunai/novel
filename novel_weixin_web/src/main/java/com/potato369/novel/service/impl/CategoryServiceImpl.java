package com.potato369.novel.service.impl;

import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.enums.CategoryIsDeleteEnum;
import com.potato369.novel.basic.repository.CategoryRepository;
import com.potato369.novel.converter.NovelCategory2CategoryDTOConverter;
import com.potato369.novel.dto.CategoryDTO;
import com.potato369.novel.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName NovelCategoryServiceImpl
 * @Desc 小说类目信息业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 15:33
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository novelCategoryRepository;

	/**
	 * <pre>
	 * 根据类目类型typeid列表查询对应的类目类型
	 * @param categoryTypeList
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return NovelCategory2CategoryDTOConverter.convertToCategoryDTOList(novelCategoryRepository.findByCategoryTypeIn(categoryTypeList));
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
		return NovelCategory2CategoryDTOConverter.convert(novelCategoryRepository.save(NovelCategory2CategoryDTOConverter.convert(categoryDTO)));
	}

	/**
	 * <pre>
	 * 根据主键categoryId删除类目信息
	 * @param categoryId
	 * </pre>
	 */
	@Override
	public CategoryDTO delete(String categoryId) {
		NovelCategory novelCategory = novelCategoryRepository.findOne(categoryId);
		novelCategory.setIsDeleted(CategoryIsDeleteEnum.DELETE.getCode());
		return NovelCategory2CategoryDTOConverter.convert(novelCategoryRepository.saveAndFlush(novelCategory));
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
		return NovelCategory2CategoryDTOConverter.convert(novelCategoryRepository.saveAndFlush(NovelCategory2CategoryDTOConverter.convert(categoryDTO)));
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
		return NovelCategory2CategoryDTOConverter.convert(novelCategoryRepository.findOne(categoryId));
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
//		return NovelCategory2CategoryDTOConverter.convert(novelCategoryRepository.findNovelCategoryByParentCategoryId(parentCategoryId));
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
		return NovelCategory2CategoryDTOConverter.convert(novelCategoryRepository.findByCategoryENText(categoryEnName));
	}

	/**
	 * <pre>
	 * 查找所有的类目信息
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findAll() {
		return NovelCategory2CategoryDTOConverter.convertToCategoryDTOList(novelCategoryRepository.findAll(new Sort(Sort.Direction.DESC, "updateTime")));
	}

	/**
	 * <pre>
	 * 查找所有的父级类目id不为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findAllParentCategoryIdIsNotNull() {
		return NovelCategory2CategoryDTOConverter.convertToCategoryDTOList(novelCategoryRepository.findNovelCategoriesByParentCategoryIdIsNotNull());
	}

	/**
	 * <pre>
	 * 查找所有的父级类目id为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	@Override
	public List<CategoryDTO> findAllParentCategoryIdIsNull() {
		return NovelCategory2CategoryDTOConverter.convertToCategoryDTOList(novelCategoryRepository.findNovelCategoriesByParentCategoryIdIsNull());
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
		return NovelCategory2CategoryDTOConverter.convertToCategoryDTOPage(novelCategoryRepository.findAll(pageable), pageable);
	}
}
