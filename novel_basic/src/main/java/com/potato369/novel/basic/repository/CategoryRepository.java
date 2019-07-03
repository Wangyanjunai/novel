package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @ClassName CategoryRepository
 * @Desc 小说类目信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 15:31
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Repository
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

    /**
     * <pre>
     * 更新点击次数和阅读次数
     * @param readingNumber 阅读次数
     * @param clickNumber 点击次数
     * @param categoryId 分类id
     * </pre>
     */
    @Modifying
    @Query(value = "update NovelCategory nc set nc.readingNumber=?1, nc.clickNumber=?2 where nc.categoryId=?3")
    void updateReadingAndClickNumber(BigDecimal readingNumber, BigDecimal clickNumber, String categoryId);

    NovelCategory findByCategoryType(Integer categoryType);
}
