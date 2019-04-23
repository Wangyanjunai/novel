package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.repository.CategoryRepository;
import com.potato369.novel.basic.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	private CategoryRepository repository;

	/**
	 * <pre>
	 * 根据类目类型typeid列表查询对应的类目类型
	 * @param categoryTypeList
	 * @return
	 * </pre>
	 */
	@Override
	public List<NovelCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		return repository.findByCategoryTypeIn(categoryTypeList);
	}

	/**
	 * <pre>
	 * 保存类目类型对象
	 * @param category
	 * @return
	 * </pre>
	 */
	@Override
	public NovelCategory save(NovelCategory category) {
		return repository.save(category);
	}

	/**
	 * <pre>
	 * 根据主键categoryId删除类目信息
	 * @param categoryId
	 * </pre>
	 */
	@Override
	public void delete(String categoryId) {
		repository.delete(categoryId);
	}

	/**
	 * <pre>
	 * 更新类目信息
	 * @param category
	 * @return
	 * </pre>
	 */
	@Override
	public NovelCategory update(NovelCategory category) {
		return repository.saveAndFlush(category);
	}

	/**
	 * <pre>
	 * 根据categoryId查询类目信息
	 * @param categoryId
	 * @return
	 * </pre>
	 */
	@Override
	public NovelCategory findOne(String categoryId) {
		return repository.findOne(categoryId);
	}

	/**
	 * <pre>
	 * 根据父级类目id查询类目信息
	 * @param parentCategoryId
	 * @return
	 * </pre>
	 */
	@Override
	public NovelCategory findByParentCategoryId(String parentCategoryId) {
		return repository.findNovelCategoryByParentCategoryId(parentCategoryId);
	}

	/**
	 * <pre>
	 * 根据类目类型text查询类目信息
	 * @param categoryEnName
	 * @return
	 * </pre>
	 */
	@Override
	public NovelCategory findByCategoryEnName(String categoryEnName) {
		return repository.findByCategoryENText(categoryEnName);
	}

	/**
	 * <pre>
	 * 查找所有的类目信息
	 * @return
	 * </pre>
	 */
	@Override
	public List<NovelCategory> findAll() {
		return repository.findAll();
	}

	/**
	 * <pre>
	 * 查找所有的父级类目id不为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	@Override
	public List<NovelCategory> findAllParentCategoryIdIsNotNull() {
		return repository.findNovelCategoriesByParentCategoryIdIsNotNull();
	}

	/**
	 * <pre>
	 * 查找所有的父级类目id为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	@Override
	public List<NovelCategory> findAllParentCategoryIdIsNull() {
		return repository.findNovelCategoriesByParentCategoryIdIsNull();
	}

	/**
	 * <pre>
	 * 分页查询所有的类目信息
	 * @param pageable
	 * @return
	 * </pre>
	 */
	@Override
	public Page<NovelCategory> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
