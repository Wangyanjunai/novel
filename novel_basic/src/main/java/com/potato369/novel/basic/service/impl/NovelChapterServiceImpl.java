package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.repository.NovelChapterRepository;
import com.potato369.novel.basic.repository.NovelInfoRepository;
import com.potato369.novel.basic.service.NovelChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public NovelChapter save(NovelChapter novelChapter) {
        return repository.save(novelChapter);
    }

    /**
     * <pre>
     * 根据小说chapterId删除小说章节信息
     * @param chapterId
     * </pre>
     */
    @Override
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
    		Integer bookId = novelChapter.getBookId();
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
    public List<NovelChapter> findAll(Integer bookId) {
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
    public Page<NovelChapter> findAll(Integer bookId, Pageable pageable) {
    	return repository.findAll(null, pageable);
    }
}