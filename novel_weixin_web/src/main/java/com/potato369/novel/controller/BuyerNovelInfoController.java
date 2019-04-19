package com.potato369.novel.controller;

import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.enums.NovelInfoEnum;
import com.potato369.novel.conf.prop.ProjectUrlProperties;
import com.potato369.novel.converter.NovelInfo2NovelInfoDTOConverter;
import com.potato369.novel.dto.CategoryDTO;
import com.potato369.novel.dto.NovelInfoDTO;
import com.potato369.novel.service.CategoryService;
import com.potato369.novel.service.NovelInfoService;
import com.potato369.novel.utils.ResultVOUtil;
import com.potato369.novel.utils.UUIDUtil;
import com.potato369.novel.vo.*;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName BuyerNovelInfoController
 * @Desc 用户端小说接口Controller
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/17 15:10
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/book")
@Slf4j
public class BuyerNovelInfoController {

	@Autowired
	private NovelInfoService novelInfoService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProjectUrlProperties projectUrlProperties;
	
	// 书城小说首页接口
	@GetMapping(value = "/home")
	//@Cacheable(cacheNames = "novelHomeData", key = "#buyerOpenid", condition = "#buyerOpenid.length() >= 14", unless = "#result.getErr_no() != 0")
	public ResultVO<HomeDataVO> home(@RequestParam(name = "buyerOpenid", required = true, defaultValue = "oSkiNv4fBXYxidv0wU_U0UDHNP4M") String buyerOpenid) {
		List<NovelInfo> novelInfoList = NovelInfo2NovelInfoDTOConverter.convertToNovelInfoList(novelInfoService.findAll());
		List<CategoryDataVO> categoryDataVOList = new ArrayList<CategoryDataVO>();
		List<CategoriesDataVO> categoriesData = new ArrayList<CategoriesDataVO>();
		Map<Integer, List<NovelInfo>> map = new HashMap<Integer, List<NovelInfo>>();
		for(NovelInfo novelInfo : novelInfoList) {
			List<NovelInfo> tempList = map.get(novelInfo.getType());
			if(tempList == null) {
				tempList = new ArrayList<NovelInfo>();
				tempList.add(novelInfo);
				map.put(novelInfo.getType(), tempList);
			} else {
				tempList.add(novelInfo);
			}
		}
		for(Integer category: map.keySet()) {
			CategoryDataVO categoryDataVO = new CategoryDataVO();
			categoryDataVO.setCategory(category);
			categoryDataVO.setListData(createRandomList(comapsData(map.get(category)), 4));
			categoryDataVOList.add(categoryDataVO);

			CategoriesDataVO categoriesDataVO = new CategoriesDataVO();
			categoriesDataVO.setCategory(category);
			categoriesDataVO.setNumber(BigDecimal.valueOf(map.get(category).size()));
			categoriesData.add(getData(category, categoriesDataVO));	
		}
		HomeDataVO homeData = new HomeDataVO();
		homeData.setBannerUrl(new StringBuffer(projectUrlProperties.getResUrl().concat("/").concat("home_banner2.jpg")).toString().trim());
		homeData.setBannerInfoListData(getBannerInfoListData(novelInfoList));
		homeData.setCategoriesData(categoriesData);
		homeData.setCategoryListData(categoryDataVOList);
		homeData.setFeaturedData(getFeaturedData(novelInfoList));
		homeData.setGuessYouLikeData(getGuessYouLikeData(novelInfoList));
		homeData.setRandomData(getRandomData(novelInfoList));
		homeData.setRecommendData(getRecommendData(novelInfoList));
		ResultVO resultVO = new ResultVO();
		resultVO.setErr_no(0);
		resultVO.setMsg("返回数据成功");
		resultVO.setData(homeData);
		resultVO.setTotal(BigDecimal.ZERO.add(new BigDecimal(8)));
		return resultVO;
	}

	// 书城小说详情接口
	@GetMapping(value = "/detail")
	//@Cacheable(cacheNames = "novelHomeDetailData", key = "#fileName", condition = "#fileName.length() >= 0", unless = "#result.getErr_no() != 0")
	public ResultVO<NovelInfoVO> detail(@RequestParam(name = "fileName", required = false) String fileName) {
		NovelInfo novelInfo = NovelInfo2NovelInfoDTOConverter.convert(novelInfoService.findByFileName(fileName));
		if (StringUtils.isNotEmpty(fileName)) {
			ResultVOUtil.error(1,"小说的文件名称不能为空！");
		}
		if (novelInfo == null) {
			ResultVOUtil.error(2,"从数据库根据小说文件名称{"+fileName+"}获取小说详情数据失败。");
		}
		NovelInfoVO novelInfoVO = coverData(novelInfo);
		ResultVO resultVO = new ResultVO();
		resultVO.setErr_no(0);
		resultVO.setMsg("从数据库根据小说文件名称{"+fileName+"}获取小说详情数据成功。");
		resultVO.setData(novelInfoVO);
		resultVO.setTotal(BigDecimal.ZERO);
		return resultVO;
	}

	// 书城小说书架数据接口
	@GetMapping(value = "/shelf")
	//@Cacheable(cacheNames = "novelHomeShelfData", key = "#buyerOpenid", condition = "#buyerOpenid.length() >= 14", unless = "#result.getErr_no() != 0")
	public ResultVO<BookListDataVO> shelf(@RequestParam(name = "buyerOpenid", required = true, defaultValue = "oSkiNv4fBXYxidv0wU_U0UDHNP4M") String buyerOpenid) {
		BookListDataVO bookListDataVO = new BookListDataVO();
		List<NovelInfoVO> novelInfoVOList = new ArrayList<NovelInfoVO>();
		bookListDataVO.setBookListData(novelInfoVOList);
		ResultVO resultVO = new ResultVO();
		resultVO.setData(bookListDataVO);
		resultVO.setErr_no(0);
		resultVO.setMsg("从数据库查询书架信息成功！");
		resultVO.setTotal(BigDecimal.valueOf(bookListDataVO.getBookListData().size()));
		return resultVO;
	}

	// 书城小说列表接口
	@GetMapping(value = "/list")
	//@Cacheable(cacheNames = "novelHomeListData", key = "#buyerOpenid", condition = "#buyerOpenid.length() >= 14", unless = "#result.getErr_no() != 0")
	public ResultVO<CategoryDataVO> list(@RequestParam(name = "buyerOpenid", required = true, defaultValue = "oSkiNv4fBXYxidv0wU_U0UDHNP4M") String buyerOpenid) {
		List<NovelInfo> novelInfoList = NovelInfo2NovelInfoDTOConverter.convertToNovelInfoList(novelInfoService.findAll());
		ResultVO resultVO = new ResultVO();
		resultVO.setMsg("查询成功");
		resultVO.setErr_no(0);
		resultVO.setTotal(BigDecimal.valueOf(novelInfoList.size()));
		Map<String, List<NovelInfo>> map = new HashMap<String, List<NovelInfo>>();
		for(NovelInfo novelInfo : novelInfoList) {
			novelInfo.setCoverURL(getCover(novelInfo));
			List<NovelInfo> tempList = map.get(novelInfo.getCategoryText());
			if(tempList == null) {
				tempList = new ArrayList<NovelInfo>();
				tempList.add(novelInfo);
				map.put(novelInfo.getCategoryText(), tempList);
			} else {
				tempList.add(novelInfo);
			}
		}
		resultVO.setData(map);
		return resultVO;
	}

	// 书城小说翻转卡片推荐小说接口
	@GetMapping(value = "/flat-list")
	//@Cacheable(cacheNames = "novelHomeFlatListData", key = "#buyerOpenid", condition = "#buyerOpenid.length() >= 14", unless = "#result.getErr_no() != 0")
	public ResultVO<List<NovelInfoVO>> flatList(@RequestParam(name = "buyerOpenid", required = true, defaultValue = "oSkiNv4fBXYxidv0wU_U0UDHNP4M") String buyerOpenid) {
		ResultVO resultVO = new ResultVO();
		List<NovelInfo> novelInfoList = NovelInfo2NovelInfoDTOConverter.convertToNovelInfoList(novelInfoService.findAll());
		resultVO.setErr_no(0);
		resultVO.setMsg("从数据库查询所有小说列表信息成功。");
		resultVO.setData(comapsData(novelInfoList));
		resultVO.setTotal(BigDecimal.valueOf(novelInfoList.size()));
		return resultVO;
	}

	// 楼兰书屋分类接口
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/category-list")
	public ResultVO<List<NovelVO>> categoryNovelInfoList() {
		try {
			if (log.isDebugEnabled()) {
				log.debug("【前端小说分类接口】开始查询生成小说分类接口数据");
			}
			ResultVO<List<NovelVO>> resultVO = new ResultVO<>();
			 int i = 0;
			List<NovelInfoDTO> novelInfoDTOs = novelInfoService.findAll();
			List<Integer> categoryTypeList = novelInfoDTOs.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
			List<CategoryDTO> categoryDTOs = categoryService.findByCategoryTypeIn(categoryTypeList);
			//拼装数据
			List<NovelVO> novelVOs = new ArrayList<>();
			for (CategoryDTO categoryDTO : categoryDTOs) {
				NovelVO novelVO = new NovelVO();
				novelVO.setCategory(categoryDTO.getCategoryType());
				novelVO.setCategoryName(categoryDTO.getCategoryName());
				novelVO.setCategoryEnName(categoryDTO.getCategoryEnName());
				List<NovelInfoVO> novelInfoVOs = new ArrayList<>();
				for (NovelInfoDTO novelInfoDTO : novelInfoDTOs) {
					if (novelInfoDTO.getCategoryType().equals(categoryDTO.getCategoryType())) {
						NovelInfoVO novelInfoVO = new NovelInfoVO();
						NovelInfo novelInfo = NovelInfo2NovelInfoDTOConverter.convert(novelInfoDTO);
						BeanUtils.copyProperties(novelInfo, novelInfoVO);
						novelInfoVOs.add(novelInfoVO);
					}
				}
				novelVO.setNovelInfoVOList(novelInfoVOs);
				novelVO.setTotal(new BigDecimal(novelInfoVOs.size()));
				novelVOs.add(novelVO);
				resultVO.setErr_no(0);
		        resultVO.setMsg("返回数据成功");
		        resultVO.setData(novelVOs);
		        i += novelInfoVOs.size();
		        resultVO.setTotal(new BigDecimal(i));
			}
			return resultVO;
		} catch (Exception e) {
			log.error("【前端小说分类接口】查询生成小说分类接口数据出现错误e={}" + e.getMessage(), e);
			return ResultVOUtil.error(-1, "返回数据失败");
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【前端小说分类接口】结束查询生成小说分类接口数据");
			}
		} 
	}
	
	private List<NovelInfoVO> getGuessYouLikeData(List<NovelInfo> novelInfoList) {
		return createRandomList(comapsData(novelInfoList), 9);
	}

	private List<NovelInfoVO> getRecommendData(List<NovelInfo> novelInfoList) {
		return createRandomList(comapsData(novelInfoList), 3);
	}

	private List<NovelInfoVO> getFeaturedData(List<NovelInfo> novelInfoList) {
		return createRandomList(comapsData(novelInfoList), 6);
	}

	private List<NovelInfoVO> getRandomData(List<NovelInfo> novelInfoList) {
		return createRandomList(comapsData(novelInfoList), 1);
	}

	private List<NovelInfoVO> comapsData(List<NovelInfo> novelInfoList) {
		List<NovelInfoVO> NovelInfoVOs = new ArrayList<NovelInfoVO>();
		for (NovelInfo novelInfo : novelInfoList) {
			NovelInfoVOs.add(coverData(novelInfo));
		}
		return NovelInfoVOs;
	}

	private NovelInfoVO coverData(NovelInfo novelInfo) {
		NovelInfoVO novelInfoVO = new NovelInfoVO();
		BeanUtils.copyProperties(novelInfo, novelInfoVO);
		novelInfoVO.setCategory(novelInfo.getCategoryType());
		novelInfoVO.setCover(getCover(novelInfo));
		novelInfoVO.setIntroduction(new StringBuffer().append("『内容简介：").append(novelInfo.getIntroduction()).append("……』").toString().trim());
		if (novelInfo.getCache() == NovelInfoEnum.CACHE.getCode()) {
			novelInfoVO.setCache(NovelInfoEnum.CACHE.getStatus());
		}
		if (novelInfo.getHaveRead() == NovelInfoEnum.HAVE_READ.getCode()) {
			novelInfoVO.setHaveRead(NovelInfoEnum.HAVE_READ.getStatus());
		}
		if (novelInfo.getSelected() == NovelInfoEnum.SELECTED.getCode()) {
			novelInfoVO.setSelected(NovelInfoEnum.SELECTED.getStatus());
		}
		if (novelInfo.getPrivated() == NovelInfoEnum.PRIVATED.getCode()) {
			novelInfoVO.setPrivated(NovelInfoEnum.PRIVATED.getStatus());
		}
		return novelInfoVO;
	}

	private static List createRandomList(List list, int n) {
		Map map = new HashMap<>();
		List listNew = new ArrayList<>();
		if (list.size() <= n) {
			return list;
		} else {
			while (map.size() < n) {
				int random = (int) (Math.random() * list.size());
				if (!map.containsKey(random)) {
					map.put(random, "");
					listNew.add(list.get(random));
				}
			}
			return listNew;
		}
	}
	
	private CategoriesDataVO getData(Integer category, CategoriesDataVO categoriesDataVO) {
		switch (category) {
		case 1:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/xh/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/xh/cover2.jpg").toString().trim());
			break;
		case 2:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/js/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/js/cover2.jpg").toString().trim());
			break;
		case 3:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/ls/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/ls/cover2.jpg").toString().trim());
			break;
		case 4:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/qh/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/qh/cover1.jpg").toString().trim());
			break;
		case 5:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/wx/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/wx/cover2.jpg").toString().trim());
			break;
		case 6:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/xx/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/xx/cover2.jpg").toString().trim());
			break;
		case 7:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/cam/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/cam/cover2.jpg").toString().trim());
			break;
		case 8:
			categoriesDataVO.setImageUrl1(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/xy/cover1.jpg").toString().trim());
			categoriesDataVO.setImageUrl2(new StringBuffer(projectUrlProperties.getResUrl()).append("/cover/xy/cover2.jpg").toString().trim());
			break;
		default:
			break;
		}
		return categoriesDataVO;
	}

	private List<BannerInfoVO> getBannerInfoListData(List<NovelInfo> novelInfoList) {
		List<BannerInfoVO> bannerInfoVOList = new ArrayList<BannerInfoVO>();
		List<NovelInfo> novelInfoLists = createRandomList(novelInfoList, 5);
		for (NovelInfo novelInfo : novelInfoLists) {
			BannerInfoVO bannerInfoVO = new BannerInfoVO();
			bannerInfoVO.setBannerId(UUIDUtil.gen32UUID());
			bannerInfoVO.setBannerImg(getCover(novelInfo));
			bannerInfoVO.setNovelInfoId(String.valueOf(novelInfo.getId()));
			bannerInfoVO.setTitle(novelInfo.getTitle());
			bannerInfoVO.setCategory(novelInfo.getCategoryText());
			bannerInfoVO.setNovelId(novelInfo.getId().toString());
			bannerInfoVOList.add(bannerInfoVO);
		}
		return bannerInfoVOList;
	}

	private String getCover(NovelInfo novelInfo) {
		return new StringBuffer(projectUrlProperties.getResUrl()
				.concat("/book/res/img/cover").concat("/")
				.concat(novelInfo.getCategoryText()).concat("/")
				.concat(novelInfo.getId().toString()).concat("/")
				.concat(novelInfo.getCoverURL())
		).toString().trim();
	}
}
