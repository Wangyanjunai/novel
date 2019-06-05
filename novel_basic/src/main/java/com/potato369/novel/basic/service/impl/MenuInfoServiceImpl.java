package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelMenuInfo;
import com.potato369.novel.basic.repository.MenuInfoRepository;
import com.potato369.novel.basic.service.MenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName MenuInfoServiceImpl
 * @Desc 公众号自定义菜单数据Service接口实现
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 9:16
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class MenuInfoServiceImpl implements MenuInfoService {

    @Autowired
    private MenuInfoRepository repository;

    /**
     * <pre>
     * 新增公众号自定义菜单数据
     * @param menuInfo
     * @return NovelMenuInfo
     * </pre>
     */
    @Override
    public NovelMenuInfo save(NovelMenuInfo menuInfo) {
        return repository.save(menuInfo);
    }

    /**
     * <pre>
     * 根据主键删除公众号自定义菜单数据
     * @param primaryKey
     * </pre>
     */
    @Override
    public void delete(String primaryKey) {
        repository.delete(primaryKey);
    }

    /**
     * <pre>
     * 修改公众号自定义菜单数据
     * @param menuInfo
     * @return NovelMenuInfo
     * </pre>
     */
    @Override
    public NovelMenuInfo update(NovelMenuInfo menuInfo) {
        return repository.saveAndFlush(menuInfo);
    }

    /**
     * <pre>
     * 根据主键查找一个公众号自定义菜单数据
     * @param primaryKey
     * @return NovelMenuInfo
     * </pre>
     */
    @Override
    public NovelMenuInfo findOne(String primaryKey) {
        return repository.findOne(primaryKey);
    }

    /**
     * <pre>
     * 查找公众号自定义菜单数据列表
     * @return List<NovelMenuInfo>
     * </pre>
     */
    @Override
    public List<NovelMenuInfo> findAll() {
        return repository.findAll();
    }

    /**
     * <pre>
     * 分页查询公众号自定义菜单数据列表
     * @param pageable
     * @return Page<NovelMenuInfo>
     * </pre>
     */
    @Override
    public Page<NovelMenuInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
