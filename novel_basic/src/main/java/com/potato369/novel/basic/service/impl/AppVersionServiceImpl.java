package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.AppVersion;
import com.potato369.novel.basic.repository.AppVersionRepository;
import com.potato369.novel.basic.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName AppVersionServiceImpl
 * @Desc App Version Service impl
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/20 17:51
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionRepository repository;

    @Override
    public List<AppVersion> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public AppVersion findById(String id) {
        return repository.findOne(id);
    }
}
