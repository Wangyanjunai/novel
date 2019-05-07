package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.repository.NovelInfoRepository;
import com.potato369.novel.basic.service.NovelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName NovelInfoServiceImpl
 * @Desc 小说信息业务Service接口实现类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/14 13:50
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class NovelInfoServiceImpl implements NovelInfoService {

    @Autowired
    private NovelInfoRepository repository;

    /**
     * 新增
     *
     * @param novelInfo
     * @return
     */
    @Override
    public NovelInfo save(NovelInfo novelInfo) {
        return repository.save(novelInfo);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
    	repository.delete(id);
    }

    /**
     * 修改
     *
     * @param novelInfo
     * @return
     */
    @Override
    public NovelInfo update(NovelInfo novelInfo) {
        return repository.saveAndFlush(novelInfo);
    }

    /**
     * 根据id查找单个小说
     *
     * @param id
     * @return
     */
    @Override
    public NovelInfo find(String id) {
        return repository.findOne(id);
    }

    /**
     * 根据小说的状态查询小说信息列表
     *
     * @param novelStatus
     * @return
     */
    @Override
    public List<NovelInfo> findByNovelStatus(Integer novelStatus) {
        return repository.findNovelInfoByNovelStatus(novelStatus);
    }

    /**
     * 查找小说列表
     *
     * @return
     */
    @Override
    public List<NovelInfo> findAll() {
        return repository.findAll();
    }

    /**
     * 查找小说分页列表
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<NovelInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
	
	@Override
	public NovelInfo findByTitleAndCategoryText(String title, String categoryText) {
		return repository.findNovelInfoByTitleAndCategoryCNText(title, categoryText);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelInfoService#getCategoryCount(java.lang.String)
	 * </pre>
	 */
		
	@Override
	public Integer getCategoryCount(String category_en_text) {
		return repository.findCountByCategoryENText(category_en_text);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelInfoService#findByCategoryEnText(java.lang.String)
	 * </pre>
	 */
		
	@Override
	public List<NovelInfo> findByCategoryEnText(String categoryENText) {
		return repository.findByCategoryENText(categoryENText);
	}

    @Override
    public List<NovelInfo> findByCategoryType(Integer type) {
        return repository.findNovelInfoByCategoryType(type);
    }

    @Override
    public Page<NovelInfo> findByCategoryType(Pageable pageable, Integer type) {
        return repository.findNovelInfoByCategoryType(pageable ,type);
    }

	@Override
	public NovelInfo findByTitleAndCategoryTextAndAuthor(String title, String categoryText, String author) {
		return repository.findNovelInfoByTitleAndCategoryCNTextAndAuthor(title, categoryText, author);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelInfoService#findNovelInfoByCategoryTypeInOrderByRetention(org.springframework.data.domain.Pageable, java.util.List)
	 * </pre>
	 */
		
	@Override
	public Page<NovelInfo> findNovelInfoByCategoryTypeInOrderByRetention(Pageable pageable, List<String> categoryTypes) {
		return repository.findNovelInfoByCategoryTypeInOrderByRetention(pageable, categoryTypes);
	}
}
