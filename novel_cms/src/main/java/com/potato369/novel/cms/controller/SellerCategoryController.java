package com.potato369.novel.cms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.potato369.novel.cms.dto.CategoryDTO;
import com.potato369.novel.cms.service.CmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.potato369.novel.basic.enums.ResultEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 类名: SellerCategoryController
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年3月29日 上午11:00:34
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Controller
@RequestMapping(value = "/category")
@Slf4j
public class SellerCategoryController {

	@Autowired
	private CmsCategoryService categoryService;
	
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
            for (CategoryDTO categoryDTO:categoryDTOPage.getContent()) {
//                categoryDTO = findByParentCategoryId(categoryDTO.getParentCategoryId(), categoryDTO);
//                categoryDTOListTemp.add(categoryDTO);
            }
            categoryDTOPage = new PageImpl<>(categoryDTOListTemp);
            List<CategoryDTO> categoryDTOList = categoryService.findAllParentCategoryIdIsNull();
            map.put("categoryDTOPage", categoryDTOPage);
            map.put("categoryDTOList", categoryDTOList);
            map.put("currentPage", page);
            map.put("size", size);
            return new ModelAndView("category/list", map);
        } catch (Exception e) {
            log.error("【卖家端后台管理】分页查询买家的小说类目信息列表出现错误，e={}", e);
            map.put("msg", ResultEnum.CATEGORY_LIST_ERROR.getMessage());
            map.put("url", "#");
            return new ModelAndView("common/error");
        } finally {
        	if (log.isDebugEnabled()) {
                log.debug("【卖家端后台管理】结束分页查询买家的小说类目信息列表，page={}，size={}", page, size);
            }
		}
	}
}
