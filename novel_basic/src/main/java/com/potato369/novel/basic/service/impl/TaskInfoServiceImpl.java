package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.TaskInfo;
import com.potato369.novel.basic.repository.TaskInfoRepository;
import com.potato369.novel.basic.service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName TaskInfoServiceImpl
 * @Desc 任务进度Service接口实现
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 9:03
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    private TaskInfoRepository repository;

    /**
     * <pre>
     * 新增任务进度记录信息
     * @param taskInfo
     * @return TaskInfo
     * </pre>
     */
    @Override
    public TaskInfo save(TaskInfo taskInfo) {
        return repository.save(taskInfo);
    }

    /**
     * <pre>
     * 根据主键删除任务进度记录信息
     * @param primaryKey
     * </pre>
     */
    @Override
    public void delete(String primaryKey) {
        repository.delete(primaryKey);
    }

    /**
     * <pre>
     * 修改任务进度记录信息
     * @param taskInfo
     * @return TaskInfo
     * </pre>
     */
    @Override
    public TaskInfo update(TaskInfo taskInfo) {
        return repository.saveAndFlush(taskInfo);
    }

    /**
     * <pre>
     * 根据主键查找一个任务进度记录信息
     * @param primaryKey
     * @return TaskInfo
     * </pre>
     */
    @Override
    public TaskInfo findOne(String primaryKey) {
        return repository.findOne(primaryKey);
    }

    /**
     * <pre>
     * 查找任务进度记录信息列表
     * @return List<TaskInfo>
     * </pre>
     */
    @Override
    public List<TaskInfo> findAll() {
        return repository.findAll();
    }

    /**
     * <pre>
     * 排序查找任务进度记录信息列表
     * @param sort
     * @return List<TaskInfo>
     * </pre>
     */
    @Override
    public List<TaskInfo> findAll(Sort sort) {
        return repository.findAll(sort);
    }
    
    
    /**
     * <pre>
     * 分页查询任务进度记录信息列表
     * @param pageable
     * @return Page<TaskInfo>
     * </pre>
     */
    @Override
    public Page<TaskInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
