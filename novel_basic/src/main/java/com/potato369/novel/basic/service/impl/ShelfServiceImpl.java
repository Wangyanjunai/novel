package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelShelf;
import com.potato369.novel.basic.repository.ShelfRepository;
import com.potato369.novel.basic.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName ShelfServiceImpl
 * @Desc ShelfServiceImpl
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 18:07
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class ShelfServiceImpl implements ShelfService {

    @Autowired
    private ShelfRepository shelfRepository;

    @Override
    public NovelShelf selectByUserId(String userId) {
        return shelfRepository.selectByUserId(userId);
    }
}
