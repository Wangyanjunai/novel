package com.potato369.novel.app.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
					List<CategoryInfoVO> categoryInfoVOs = new ArrayList<CategoryInfoVO>();
					List<NovelCategory> subCategories = categoryService.findByParentCategoryId(categoryId);
					for (NovelCategory novelCategory2 : subCategories) {
						CategoryInfoVO categoryInfoVO = new CategoryInfoVO();
						categoryInfoVO.setName(novelCategory2.getCategoryCNText());
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
			resultVO.setData(null);
			resultVO.setCode(1);
			resultVO.setMsg("获取数据失败");
			log.error("【急速追书后台管理】获取获取所有的分类出现错误", e);
			return resultVO;
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【急速追书后台管理】end====================获取所有的分类====================end");
			}			
		}
	}
}
