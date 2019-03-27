package com.potato369.novel.basic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.potato369.novel.basic.dataobject.NovelCategory;
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
	List<NovelCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

	/**
	 * <pre>
	 * 保存类目类型对象
	 * @param category
	 * @return
	 * </pre>
	 */
	NovelCategory save(NovelCategory category);

	/**
	 * <pre>
	 * 根据主键categoryId删除类目信息
	 * @param categoryId
	 * </pre>
	 */
	void delete(String categoryId);

	/**
	 * <pre>
	 * 更新类目信息
	 * @param category
	 * @return
	 * </pre>
	 */
	NovelCategory update(NovelCategory category);

	/**
	 * <pre>
	 * 根据categoryId查询类目信息
	 * @param categoryId
	 * @return
	 * </pre>
	 */
	NovelCategory findOne(String categoryId);

	/**
	 * <pre>
	 * 根据父级类目id查询类目信息
	 * @param parentCategoryId
	 * @return
	 * </pre>
	 */
	NovelCategory findByParentCategoryId(String parentCategoryId);

	/**
	 * <pre>
	 * 根据类目类型text查询类目信息
	 * @param categoryEnName
	 * @return
	 * </pre>
	 */
	NovelCategory findByCategoryEnName(String categoryEnName);

	/**
	 * <pre>
	 * 查找所有的类目信息
	 * @return
	 * </pre>
	 */
	List<NovelCategory> findAll();

	/**
	 * <pre>
	 * 查找所有的父级类目id不为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	List<NovelCategory> findAllParentCategoryIdIsNotNull();

	/**
	 * <pre>
	 * 查找所有的父级类目id为Null的类目信息列表
	 * @return
	 * </pre>
	 */
	List<NovelCategory> findAllParentCategoryIdIsNull();

	/**
	 * <pre>
	 * 分页查询所有的类目信息
	 * @param pageable
	 * @return
	 * </pre>
	 */
	Page<NovelCategory> findAll(Pageable pageable);
}
