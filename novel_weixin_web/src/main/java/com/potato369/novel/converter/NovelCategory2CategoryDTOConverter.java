package com.potato369.novel.converter;

import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.dto.CategoryDTO;
import com.potato369.novel.utils.StringUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
/**
 * <pre>
 * @PackageName com.potato369.novel.converter
 * @ClassName NovelCategory2CategoryDTOConverter
 * @Desc 将类目信息转换为类目信息DTO转化器
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/18 13:50
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class NovelCategory2CategoryDTOConverter {

    /**
     * <pre>
     * 将NovelCategory对象转换为CategoryDTO对象
     * @param novelCategory
     * @return CategoryDTO对象
     * </pre>
     */
    public static CategoryDTO convert(NovelCategory novelCategory) {
        CategoryDTO categoryDTO = CategoryDTO.builder().build();
        BeanUtils.copyProperties(novelCategory, categoryDTO);
        categoryDTO.setCategoryId1(StringUtil.replaceString2Star(categoryDTO.getCategoryId(), 4, 4));
        return categoryDTO;
    }

    /**
     * <pre>
     * 将List<NovelCategory>对象转换为List<CategoryDTO>对象
     * @param novelCategoryList
     * @return List<CategoryDTO>对象
     * </pre>
     */
    public static List<CategoryDTO> convertToCategoryDTOList(List<NovelCategory> novelCategoryList) {
        return novelCategoryList.stream().map(novelCategory -> convert(novelCategory)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<NovelCategory>对象转换为Page<CategoryDTO>对象
     * @param novelCategoryPage
     * @return Page<NovelCategory>对象
     * </pre>
     */
    public static Page<CategoryDTO> convertToCategoryDTOPage(Page<NovelCategory> novelCategoryPage, Pageable pageable) {
        return new PageImpl<>(convertToCategoryDTOList(novelCategoryPage.getContent()), pageable, novelCategoryPage.getTotalElements());
    }

    /**
     * <pre>
     * 将CategoryDTO对象转换为NovelCategory对象
     * @param categoryDTO
     * @return NovelCategory对象
     * </pre>
     */
    public static NovelCategory convert(CategoryDTO categoryDTO) {
        NovelCategory novelCategory = NovelCategory.builder().build();
        BeanUtils.copyProperties(categoryDTO, novelCategory);
        return novelCategory;
    }

    /**
     * <pre>
     * 将List<CategoryDTO>对象转换为List<NovelCategory>对象
     * @param categoryDTOList
     * @return List<NovelCategory>对象
     * </pre>
     */
    public static List<NovelCategory> convertToNovelCategoryList(List<CategoryDTO> categoryDTOList) {
        return categoryDTOList.stream().map(categoryDTO -> convert(categoryDTO)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<CategoryDTO>对象转换为Page<NovelCategory>对象
     * @param categoryDTOPage
     * @return Page<NovelCategory>对象
     * </pre>
     */
    public static Page<NovelCategory> convertToNovelCategoryPage(Page<CategoryDTO> categoryDTOPage, Pageable pageable) {
        return new PageImpl<>(convertToNovelCategoryList(categoryDTOPage.getContent()), pageable, categoryDTOPage.getTotalElements());
    }
}
