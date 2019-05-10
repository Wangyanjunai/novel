package com.potato369.novel.app.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.potato369.novel.basic.dataobject.NovelCategory;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.service.CategoryService;
import com.potato369.novel.basic.service.NovelInfoService;
import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * 类名: CategoryController
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月28日 上午10:27:52
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value ="/cats/lv2")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private NovelInfoService novelInfoService;

	/**
	 * <pre>
	 * list方法的作用：获取所有的分类
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @since JDK 1.6
	 * </pre>
	 */
	@GetMapping(value = "/statistics/{type}")//type：male, female,picture，查询分类信息
	public ResultVO<CategoryVO> list(
			@PathVariable(name = "type") String type) {
		ResultVO<CategoryVO> resultVO = new ResultVO<>();
		try {
			if (log.isDebugEnabled()) {
				log.debug("【急速追书后台管理】start====================获取所有的分类====================start");
			}
			List<CategoryInfoVO> CategoryInfoVOs = new ArrayList<>();
			CategoryVO categoryVO = new CategoryVO();
			NovelCategory novelCategory = categoryService.findByCategoryEnName(type);
			String categoryId = novelCategory.getCategoryId();
			List<NovelCategory> subCategories = categoryService.findByParentCategoryId(categoryId);
			for (NovelCategory novelCategory2 : subCategories) {
				CategoryInfoVO categoryInfoVO = new CategoryInfoVO();
				categoryInfoVO.setName(novelCategory2.getCategoryCNText());
				categoryInfoVO.setId(novelCategory2.getCategoryId());
				CategoryInfoVOs.add(categoryInfoVO);
			}
			categoryVO.setCategoryInfoVOs(CategoryInfoVOs);
			resultVO.setData(categoryVO);
			resultVO.setCode(0);
			resultVO.setMsg("获取数据成功");
			return resultVO;
		} catch (Exception e) {
			log.error("【急速追书后台管理】获取获取所有的分类出现错误", e);
			return ResultVOUtil.error(-1, "获取数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【急速追书后台管理】end====================获取所有的分类====================end");
			}
		}
	}

	/**
	 * <pre>
	 * @param categoryId
	 * @param page
	 * @param size
	 * @return
	 * @api
	 * </pre>
	 */
	@GetMapping(value = "/statistics/category/{categoryId}")//查询每分类下的小说，传入分类id
	public ResultVO<CategoryBookVO> getCategory(@PathVariable(name = "categoryId") String categoryId,
												@RequestParam(name = "page", defaultValue = "1") Integer page,
												@RequestParam(name = "size", defaultValue = "10") Integer size) {
		ResultVO<CategoryBookVO> resultVO = new ResultVO<CategoryBookVO>();
		try {
			if (log.isDebugEnabled()) {
				log.debug("【按照类别查询小说分类】查询小说分类categoryId={}, page={}, size={}", categoryId, page, size);
			}
			NovelCategory category = categoryService.findOne(categoryId);
			CategoryBookVO categoryBookVO = CategoryBookVO.builder().build();
			if (category != null) {
				Integer type = category.getCategoryType();
				Sort sort = new Sort(Sort.Direction.DESC, "clickNumber", "readers", "recentReaders", "retention", "createTime", "updateTime");
				PageRequest pageRequest = new PageRequest(page - 1, size, sort);
				Page<NovelInfo> novelInfoPage = novelInfoService.findByCategoryType(pageRequest, type);
				List<NovelInfo> novelInfoList = novelInfoPage.getContent();
				List<NovelInfoVO> novelInfoVOList = new ArrayList<>();
				if (novelInfoList != null && !novelInfoList.isEmpty() && novelInfoList.size() > 0) {
					for (NovelInfo novelInfo : novelInfoList) {
						NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
						BeanUtils.copyProperties(novelInfo, novelInfoVO);
						novelInfoVOList.add(novelInfoVO);
					}
				}
				categoryBookVO.setBooks(novelInfoVOList);
				categoryBookVO.setTotalPage(new BigDecimal(novelInfoPage.getTotalPages()));
				resultVO.setData(categoryBookVO);
				resultVO.setCode(0);
				resultVO.setMsg("获取数据成功");
				return resultVO;
			} else {
				resultVO.setCode(-2);
				resultVO.setMsg("获取此分类的小说不存在");
				resultVO.setData(null);
				return resultVO;
			}
		} catch (Exception e) {
			log.error("【按照类别查询小说分类】查询小说分类出现错误", e);
			return ResultVOUtil.error(-1, "获取数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【按照类别查询小说分类】查询小说分类categoryId={}, page={}, size={}", categoryId, page, size);
			}
		}
	}

	/**
	 * <pre>
	 * @param categoryType
	 * @param page
	 * @param size
	 * @return
	 * @api
	 * </pre>
	 */
	@GetMapping(value = "/statistics/category/all/{categoryType}")//查询父级分类下的所有小说信息
	public ResultVO<CategoryBookVO> getAllCategory(@PathVariable(name = "categoryType") String categoryType,
												   @RequestParam(name = "page", defaultValue = "1") Integer page,
												   @RequestParam(name = "size", defaultValue = "10") Integer size) {
		ResultVO<CategoryBookVO> resultVO = new ResultVO<CategoryBookVO>();
		try {
			if (log.isDebugEnabled()) {
				log.debug("【按照类别查询小说分类】查询小说分类, page={}, size={}", page, size);
			}
			NovelCategory parentNovelCategory = categoryService.findByCategoryEnName(categoryType);
			String parentId = null;
			if (parentNovelCategory != null) {
				parentId = parentNovelCategory.getCategoryId();
				List<NovelCategory> categories = categoryService.findByParentCategoryId(parentId);
				List<Integer> cateTypeList = new ArrayList<>();
				for (NovelCategory category : categories) {
					cateTypeList.add(category.getCategoryType());
				}
				Sort sort = new Sort(Sort.Direction.DESC, "clickNumber", "readers", "recentReaders", "retention", "createTime", "updateTime");
				PageRequest pageRequest = new PageRequest(page - 1, size, sort);
				Page<NovelInfo> novelInfoPage = novelInfoService.findNovelInfoByCategoryTypeIn(pageRequest, cateTypeList);
				List<NovelInfo> novelInfoList = novelInfoPage.getContent();
				CategoryBookVO categoryBookVO = CategoryBookVO.builder().build();
				List<NovelInfoVO> novelInfoVOList = new ArrayList<>();
				if (novelInfoList != null && !novelInfoList.isEmpty() && novelInfoList.size() > 0) {
					for (NovelInfo novelInfo : novelInfoList) {
						NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
						BeanUtils.copyProperties(novelInfo, novelInfoVO);
						novelInfoVOList.add(novelInfoVO);
					}
					categoryBookVO.setBooks(novelInfoVOList);
					categoryBookVO.setTotalPage(new BigDecimal(novelInfoPage.getTotalPages()));
					resultVO.setData(categoryBookVO);
					resultVO.setCode(0);
					resultVO.setMsg("获取数据成功");
				}
			}
//				log.info("size={}", categories.size());
//				CategoryBookVO categoryBookVO = CategoryBookVO.builder().build();
//				List<NovelInfoVO> novelInfoVOList = new ArrayList<>();
//				if (categories != null && !categories.isEmpty() && categories.size() > 0) {
//					for (NovelCategory category:categories) {
//						Integer type = category.getCategoryType();
//						Sort sort = new Sort(Sort.Direction.DESC, "clickNumber", "readers", "recentReaders", "retention", "createTime", "updateTime");
//						PageRequest pageRequest = new PageRequest(page - 1, size, sort);
//						Page<NovelInfo> novelInfoPage = novelInfoService.findByCategoryType(pageRequest, type);
////						log.info("page size={}", novelInfoPage.getSize());
//						List<NovelInfo> novelInfoList = novelInfoPage.getContent();
//						if (novelInfoList != null && !novelInfoList.isEmpty() && novelInfoList.size() > 0) {
//							for (NovelInfo novelInfo:novelInfoList) {
//								NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
//								BeanUtils.copyProperties(novelInfo, novelInfoVO);
//								novelInfoVOList.add(novelInfoVO);
//
//							}
//						}
//						log.info("novelInfoList size={}", novelInfoList.size());
//						categoryBookVO.setBooks(novelInfoVOList);
//						categoryBookVO.setTotalPage(new BigDecimal(novelInfoPage.getTotalPages()));
//					}
//					resultVO.setData(categoryBookVO);
//					resultVO.setCode(0);
//					resultVO.setMsg("获取数据成功");
//				}
//			} else {
//				resultVO.setCode(-2);
//				resultVO.setMsg("获取此分类的小说不存在");
//				resultVO.setData(null);
//			}
			return resultVO;
		} catch (Exception e) {
			log.error("【按照类别查询小说分类】查询小说分类出现错误", e);
			return ResultVOUtil.error(-1, "获取数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【按照类别查询小说分类】查询小说分类page={}, size={}", page, size);
			}
		}
	}
}
