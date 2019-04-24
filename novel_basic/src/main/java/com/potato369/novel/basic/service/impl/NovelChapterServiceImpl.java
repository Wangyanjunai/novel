package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.repository.NovelChapterRepository;
import com.potato369.novel.basic.repository.NovelInfoRepository;
import com.potato369.novel.basic.service.NovelChapterService;
import cn.wanghaomiao.seimi.struct.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName NovelChapterServiceImpl
 * @Desc NovelChapterService，小说章节信息service接口实现
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/3/28 14:21
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class NovelChapterServiceImpl implements NovelChapterService {

    @Autowired
    private NovelChapterRepository repository;
    
    @Autowired
    private NovelInfoRepository novelInfoRepository;

    /**
     * <pre>
     * 新增小说章节信息
     * @param novelChapter
     * @return NovelChapter.class
     * </pre>
     */
    @Override
    @Transactional
    public NovelChapter save(NovelChapter novelChapter) {
        return repository.save(novelChapter);
    }
    
    /**
     * <pre>
     * 新增小说章节信息
     * @param response
     * @return NovelChapter.class
     * </pre>
     */
    @Override
    @Transactional
    public NovelChapter save(Response response, String bookId) {
//    	NovelChapter novelChapterTemp = null;
//    	try {
//    		novelChapterTemp = response.render(NovelChapter.class);
//    		log.info("bean resolve res={}, url={}", novelChapterTemp, response.getUrl());
//    		if (novelChapterTemp != null) {
//    			novelChapterTemp.setId(UUIDUtil.gen32UUID());
//    			novelChapterTemp.setBookId(bookId);
//    			String htmlContent = null;
//    			if(StringUtils.isNotBlank(novelChapterTemp.getContent())){
//    				//novelChapterTemp.setStarturl(BusinessConstants.CURRENT_GET_DATA_URL);
//    				novelChapterTemp.setUrl(BusinessConstants.CURRENT_GET_BOOK_DATA_URL);
//                    htmlContent = novelChapterTemp.getContent();
//                    //内容不为空的时候转化
//                    novelChapterTemp.setChapterContent(FlexmarkHtmlParser.parse(htmlContent));
//                }
//    			novelChapterTemp = save(novelChapterTemp);
//    			log.info("store success, chapterId={}, chaperName={}", novelChapterTemp.getChapterId(), novelChapterTemp.getChaperName());
//			}
//		} catch (Exception e) {
//			log.error("【后台管理】爬取小说章节信息失败", e);
//		} finally {
//		}
//        return novelChapterTemp;
    	return null;
    }

    /**
     * <pre>
     * 根据小说chapterId删除小说章节信息
     * @param chapterId
     * </pre>
     */
    @Override
    @Modifying
    @Transactional
    public void delete(String chapterId) {
    	repository.delete(chapterId);
    }

    /**
     * <pre>
     * 更新小说章节信息
     * @param novelChapter
     * @return NovelChapter.class
     * </pre>
     */
    @Override
    @Modifying
    @Transactional
    public NovelChapter update(NovelChapter novelChapter) {
        return repository.saveAndFlush(novelChapter);
    }

    /**
     * <pre>
     * 根据chapterId主键查找小说章节信息
     * @param chapterId
     * @return NovelChapter.class
     * </pre>
     */
    @Override
    public NovelChapter findNovelChapterByChapterId(String chapterId) {
        return repository.findOne(chapterId);
    }

    /**
     * <pre>
     * 根据chapterId主键查找小说章节信息
     * @param chapterId
     * @return NovelChapter.class
     * </pre>
     */
    @Override
    public NovelInfo findNovelInfoByChapterId(String chapterId) {
    	NovelChapter novelChapter = repository.findOne(chapterId);
    	NovelInfo novelInfo = null;
    	if (novelChapter != null) {
    		String bookId = novelChapter.getBookId();
    		novelInfo = novelInfoRepository.findOne(bookId);
		}
        return novelInfo;
    }

    /**
     * <pre>
     * 查找bookid对应的一本小说的所有的章节信息
     * @return NovelChapter.class
     * </pre>
     *
     * @param bookId
     */
    @Override
    public List<NovelChapter> findAll(String bookId) {
        return repository.findByBookId(bookId);
    }

    /**
     * <pre>
     * 查找bookid对应的一本小说的所有的章节信息
     * @return NovelChapter.class
     * </pre>
     *
     * @param bookId
     * @param pageable
     */
    @Override
    public Page<NovelChapter> findAll(String bookId, Pageable pageable) {
    	return repository.findAll(null, pageable);
    }

	/**
	 * <pre>
	 * 描述该方法的实现功能：根据小说章节标题，查询小说信息列表
	 * @param 章节标题（名称）
	 * @see com.potato369.novel.basic.service.NovelChapterService#findChapterByChaperName(java.lang.String)
	 * </pre>
	 */
	@Override
	public List<NovelChapter> findByChaperTitle(String title) {
		Sort sort = new Sort(Direction.DESC, "createTime", "updateTime");
		return repository.findByTitle(title, sort);
	}
}
