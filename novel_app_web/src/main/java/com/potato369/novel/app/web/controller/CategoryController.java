package com.potato369.novel.app.web.controller;

import java.util.ArrayList;
import java.util.List;

import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.NovelInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import com.potato369.novel.app.web.vo.CategoryInfoVO;
import com.potato369.novel.app.web.vo.CategoryVO;
import com.potato369.novel.app.web.vo.ResultVO;
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
	@GetMapping(value = "/statistics")
	public ResultVO<List<CategoryVO>> list() {
		ResultVO<List<CategoryVO>> resultVO = new ResultVO<List<CategoryVO>>();
		try {
			if (log.isDebugEnabled()) {
				log.debug("【急速追书后台管理】start====================获取所有的分类====================start");
			}
			List<CategoryVO> CategoryVOs = new ArrayList<>();
			List<NovelCategory> parentCategories = categoryService.findAllParentCategoryIdIsNull();
			if (parentCategories != null && !parentCategories.isEmpty() && parentCategories.size() > 0) {
				for (NovelCategory novelCategory : parentCategories) {
					CategoryVO categoryVO = new CategoryVO();
					categoryVO.setCategoryName(novelCategory.getCategoryENText());
					String categoryId = novelCategory.getCategoryId();
					categoryVO.setId(categoryId);
					List<CategoryInfoVO> categoryInfoVOs = new ArrayList<CategoryInfoVO>();
					List<NovelCategory> subCategories = categoryService.findByParentCategoryId(categoryId);
					for (NovelCategory novelCategory2 : subCategories) {
						CategoryInfoVO categoryInfoVO = new CategoryInfoVO();
						categoryInfoVO.setName(novelCategory2.getCategoryCNText());
						categoryInfoVO.setId(novelCategory2.getCategoryId());
						categoryInfoVO.setParentId(novelCategory2.getParentCategoryId());
						List<NovelInfo> novelInfos = novelInfoService.findByCategoryEnText(novelCategory2.getCategoryENText());
						if (novelInfos != null && !novelInfos.isEmpty() && novelInfos.size() > 0) {
							categoryInfoVO.setBookCount(novelInfos.size());
							categoryInfoVO.setBookCover(new String[] {novelInfos.get(0).getCoverURL(), novelInfos.get(0).getCoverURL(), novelInfos.get(0).getCoverURL()});
							categoryInfoVO.setIcon(novelInfos.get(0).getCoverURL());
							categoryInfoVO.setMonthlyCount(novelInfos.size());
						} else {
							categoryInfoVO.setBookCount(0);
							categoryInfoVO.setBookCover(new String[] {"https://fm.88dush.com/116/116830/116830s.jpg", "https://fm.88dush.com/116/116830/116830s.jpg", "https://fm.88dush.com/116/116830/116830s.jpg"});
							categoryInfoVO.setIcon("https://fm.88dush.com/116/116830/116830s.jpg");
							categoryInfoVO.setMonthlyCount(0);
						}
						categoryInfoVOs.add(categoryInfoVO);
					}
					categoryVO.setCategoryInfoVOs(categoryInfoVOs);
					CategoryVOs.add(categoryVO);
				}
				resultVO.setData(CategoryVOs);
				resultVO.setCode(0);
				resultVO.setMsg("获取数据成功");
			}
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

	@GetMapping(value = "/statistics/{categoryId}/{page}/{size}")
	public ResultVO<List<NovelInfoVO>> getCategories(@PathVariable(name = "categoryId") String categoryId,
													 @PathVariable(name = "page") Integer page,
													 @PathVariable(name = "size") Integer size) {
		ResultVO<List<NovelInfoVO>> resultVO = new ResultVO<>();
		try {
			if (log.isDebugEnabled()) {
				log.debug("【按照类别查询小说分类】查询小说分类categoryId={}, page={}, size={}", categoryId, page, size);
			}
			NovelCategory category = categoryService.findOne(categoryId);
			if (category != null) {
				Integer type = category.getCategoryType();
				Sort sort = new Sort(Sort.Direction.DESC, "clickNumber", "readers", "recentReaders", "retention", "createTime", "updateTime");
				PageRequest pageRequest = new PageRequest(page-1, size, sort);
				Page<NovelInfo> novelInfoPage = novelInfoService.findByCategoryType(pageRequest, type);
				List<NovelInfo> novelInfoList = novelInfoPage.getContent();
				List<NovelInfoVO> novelInfoVOList = new ArrayList<>();
				if (novelInfoList != null && !novelInfoList.isEmpty() && novelInfoList.size() > 0) {
					for (NovelInfo novelInfo:novelInfoList) {
						NovelInfoVO novelInfoVO = NovelInfoVO.builder().build();
						BeanUtils.copyProperties(novelInfo, novelInfoVO);
						novelInfoVOList.add(novelInfoVO);
					}
				}
				resultVO.setData(novelInfoVOList);
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
}
