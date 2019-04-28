package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @ClassName CategoryRepository
 * @Desc 小说类目信息Repository数据库操作类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 15:31
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface CategoryRepository extends JpaRepository<NovelCategory, String> {

    /**
     * <pre>
     * 根据类目类型Type列表查找所有的类目信息列表
     * @param categoryTypeList
     * @return
     * </pre>
     */
    List<NovelCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * <pre>
     * 查找所有的父级类目id为Null的类目信息列表
     * @return
     * </pre>
     */
    List<NovelCategory> findNovelCategoriesByParentCategoryIdIsNull();

    /**
     * <pre>
     * 查找所有的父级类目id不为Null的类目信息列表
     * @return
     * </pre>
     */
    List<NovelCategory> findNovelCategoriesByParentCategoryIdIsNotNull();

    /**
     * <pre>
     * @param parentCategoryId
     * @return NovelCategory
     * </pre>
     */
    List<NovelCategory> findNovelCategoryByParentCategoryId(String parentCategoryId);

    /**
     * <pre>
     * @param categoryENText
     * @return
     * </pre>
     */
    NovelCategory findByCategoryENText(String categoryENText);
}
