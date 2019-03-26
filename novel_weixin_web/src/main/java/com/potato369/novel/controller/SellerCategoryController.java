package com.potato369.novel.controller;

import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.converter.NovelCategory2CategoryDTOConverter;
import com.potato369.novel.dto.CategoryDTO;
import com.potato369.novel.exception.SellerCategoryException;
import com.potato369.novel.form.CategoryForm;
import com.potato369.novel.service.CategoryService;
import com.potato369.novel.utils.UUIDUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName SellerNovelCategoryController
 * @Desc 卖家类目信息Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/18 11:27
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Controller
@RequestMapping(value = "/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * <pre>
     * 后台分页显示类目信息列表
     * @param page
     * @param size
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/list")
    public ModelAndView list(@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
                             @RequestParam(name = "size", required = true, defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】开始分页查询买家的小说类目信息列表，page={}，size={}", page, size);
        }
        try {
            Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
            PageRequest pageRequest = new PageRequest(page - 1, size, sort);
            Page<CategoryDTO> categoryDTOPage = categoryService.findAll(pageRequest);
            List<CategoryDTO> categoryDTOListTemp = new ArrayList<>();
            for (CategoryDTO categoryDTO : categoryDTOPage.getContent()) {
                categoryDTO = findByParentCategoryId(categoryDTO.getParentCategoryId(), categoryDTO);
                categoryDTOListTemp.add(categoryDTO);
            }
            categoryDTOPage = new PageImpl<>(categoryDTOListTemp);
            List<CategoryDTO> categoryDTOList = categoryService.findAllParentCategoryIdIsNull();
            map.put("categoryDTOPage", categoryDTOPage);
            map.put("categoryDTOList", categoryDTOList);
            map.put("currentPage", page);
            map.put("size", size);
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】结束分页查询买家的小说类目信息列表，page={}，size={}", page, size);
            }
            return new ModelAndView("category/list", map);
        } catch (Exception e) {
            log.error("【卖家端后台管理】分页查询买家的小说类目信息列表出现错误，e={}", e);
            map.put("msg", ResultEnum.CATEGORY_LIST_ERROR.getMessage());
            map.put("url", "/novel/error");
            return new ModelAndView("common/error");
        }
    }

    /**
     * <pre>
     * 修改类目信息
     * @param categoryId
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/index")
    public ModelAndView index(@RequestParam(name = "categoryId", required = false) String categoryId, Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】开始查询父级类目信息不存在的类目信息列表");
        }
        try {
            List<CategoryDTO> categoryDTOList = categoryService.findAllParentCategoryIdIsNull();
            map.put("categoryDTOList", categoryDTOList);
        } catch (Exception e) {
            log.error("【卖家端后台管理】查询父级类目信息不存在的类目信息列表出现错误，e={}", e);
            map.put("msg", ResultEnum.CATEGORY_PARENT_ERROR.getMessage());
            map.put("url", "/novel/error");
            return new ModelAndView("common/error");
        }
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】结束查询父级类目信息不存在的类目信息列表");
        }
        if (categoryId != null) {
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】开始查询类目信息");
            }
            try {
                CategoryDTO categoryDTO = categoryService.findOne(categoryId);
                if (categoryDTO == null) {
                    log.error("【卖家端后台管理】查询类目信息不存在， categoryId={}", categoryId);
                    throw new SellerCategoryException(ResultEnum.CATEGORY_SAVE_OR_UPDATE_FAIL);
                }
                map.put("categoryDTO", categoryDTO);
                if (log.isDebugEnabled()) {
                    log.debug("【卖家端后台管理】结束查询类目信息");
                }
            } catch (Exception e) {
                log.error("【卖家端后台管理】查询类目信息出现错误，e={}", e);
                map.put("msg", ResultEnum.CATEGORY_SAVE_OR_UPDATE_FAIL.getMessage());
                map.put("url", "/novel/error");
                return new ModelAndView("common/error");
            }
        }
        return new ModelAndView("category/index", map);
    }

    /**
     * <pre>
     * 删除类目，只是逻辑删除，改变类目的是否删除状态为是
     * @param categoryId
     * @param map
     * @return
     * </pre>
     */
    @GetMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(name = "categoryId", required = false) String categoryId, Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】开始逻辑删除类目信息");
        }
        try {
            if (StringUtils.isEmpty(categoryId)) {
                log.error("【卖家端后台管理】后台参数错误，categoryId不能为空");
                map.put("msg", ResultEnum.PARAM_ERROR.getMessage());
                map.put("url", "/novel/error");
                return new ModelAndView("common/error");
            }
            CategoryDTO categoryDTO = categoryService.findOne(categoryId);
            if (categoryDTO == null) {
                log.error("【卖家端后台管理】根据categoryId={}查询类目信息不存在", categoryId);
                map.put("msg", ResultEnum.CATEGORY_NOT_EXIST.getMessage());
                map.put("url", "/novel/error");
                return new ModelAndView("common/error");
            }
            CategoryDTO categoryDTOResult = categoryService.delete(categoryId);
            if (categoryDTOResult == null) {
                log.error("【卖家端后台管理】根据categoryId={}删除类目信息出现错误", categoryId);
                map.put("msg", ResultEnum.CATEGORY_SAVE_OR_UPDATE_FAIL.getMessage());
                map.put("url", "/novel/error");
                return new ModelAndView("common/error");
            }
            map.put("msg", ResultEnum.CATEGORY_SAVE_OR_UPDATE_SUCCESS.getMessage());
            map.put("url", "/novel/category/list");
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】结束逻辑删除类目信息");
            }
            return new ModelAndView("common/success");
        } catch (Exception e) {
            log.error("【卖家端后台管理】删除类目出现错误，e={}", e);
            map.put("msg", ResultEnum.CATEGORY_SAVE_OR_UPDATE_FAIL.getMessage());
            map.put("url", "/novel/error");
            return new ModelAndView("common/error");
        }
    }

    /**
     * <pre>
     * 修改或者新增类目信息
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     * </pre>
     */
    @PostMapping(value = "/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult, Map<String, Object> map) {
        if (log.isDebugEnabled()) {
            log.debug("【卖家端后台管理】开始修改或者新增类目信息");
        }
        NovelCategory novelCategory = null;
        try {
            if (bindingResult.hasErrors()) {
                map.put("msg", bindingResult.getFieldError().getDefaultMessage());
                map.put("url", "/novel/category/index");
                return new ModelAndView("common/error", map);
            }
            if (StringUtils.isNotEmpty(categoryForm.getCategoryId())) {
                novelCategory = NovelCategory2CategoryDTOConverter.convert(categoryService.findOne(categoryForm.getCategoryId()));
                map.put("msg", ResultEnum.CATEGORY_UPDATE_SUCCESS.getMessage());
            } else {
                novelCategory = NovelCategory.builder().build();
                categoryForm.setCategoryId(UUIDUtil.gen32UUID());
                map.put("msg", ResultEnum.CATEGORY_SAVE_SUCCESS.getMessage());
            }
            BeanUtils.copyProperties(categoryForm, novelCategory);
            CategoryDTO categoryDTO = NovelCategory2CategoryDTOConverter.convert(novelCategory);
            categoryService.save(categoryDTO);
            map.put("url", "/novel/category/list");
            if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】结束修改或者新增类目信息");
            }
            return new ModelAndView("common/success", map);
        } catch (Exception e) {
            log.error("【卖家端后台管理】修改或者新增类目信息出现错误");
            map.put("msg", ResultEnum.CATEGORY_SAVE_OR_UPDATE_FAIL.getMessage());
            map.put("url", "/novel/category/index");
            return new ModelAndView("common/error", map);
        }
    }

    /**
     * <pre>
     * 根据parentCategoryId父级类目id查询类目信息
     * @param parentCategoryId
     * @return
     * </pre>
     */
    private CategoryDTO findByParentCategoryId(String parentCategoryId, CategoryDTO categoryDTO) {
        if (StringUtils.isEmpty(parentCategoryId)) {
            categoryDTO.setParentCategoryId("");
            categoryDTO.setParentCategoryName("不存在");
        } else {
            CategoryDTO categoryDTOResult = categoryService.findOne(parentCategoryId);
            categoryDTO.setParentCategoryId(categoryDTOResult.getCategoryId());
            categoryDTO.setParentCategoryName(categoryDTOResult.getCategoryName());
        }
        return categoryDTO;
    }
}
