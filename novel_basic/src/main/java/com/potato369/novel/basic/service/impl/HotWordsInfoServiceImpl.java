package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.HotWordsInfo;
import com.potato369.novel.basic.repository.HotWordsInfoRepository;
import com.potato369.novel.basic.service.HotWordsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName HotWordsInfoServiceImpl
 * @Desc HotWordsInfoServiceImpl
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 11:11
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class HotWordsInfoServiceImpl implements HotWordsInfoService {

    @Autowired
    private HotWordsInfoRepository repository;

    @Override
    public Page<HotWordsInfo> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public String findByWord(String words) {
        return repository.selectWord(words);
    }

    @Override
    public HotWordsInfo findByWordId(String wordId) {
        return repository.findOne(wordId);
    }

    @Override
    public HotWordsInfo save(HotWordsInfo hotWordsInfo) {
        return repository.save(hotWordsInfo);
    }

    @Override
    public HotWordsInfo update(HotWordsInfo hotWordsInfo) {
        return repository.saveAndFlush(hotWordsInfo);
    }
}
