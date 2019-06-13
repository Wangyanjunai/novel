package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.TaskRecordInfo;
import com.potato369.novel.basic.repository.TaskRecordInfoRepository;
import com.potato369.novel.basic.service.TaskRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName TaskRecordInfoServiceImpl
 * @Desc 任务进度记录Service接口实现
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/13 15:52
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class TaskRecordInfoServiceImpl implements TaskRecordInfoService {

    @Autowired
    private TaskRecordInfoRepository taskRecordInfoRepository;

    /**
     * <pre>
     * 新增任务记录信息
     * @param taskRecordInfo
     * @return TaskRecordInfo.class
     * </pre>
     */
    @Override
    public TaskRecordInfo save(TaskRecordInfo taskRecordInfo) {
        return taskRecordInfoRepository.save(taskRecordInfo);
    }

    /**
     * <pre>
     * 根据主键删除任务记录信息
     * @param primaryKey
     * @return
     * </pre>
     */
    @Override
    public void delete(String primaryKey) {
        taskRecordInfoRepository.delete(primaryKey);
    }

    /**
     * <pre>
     * 修改任务记录信息
     * @param taskRecordInfo
     * @return TaskRecordInfo.class
     * </pre>
     */
    @Override
    public TaskRecordInfo update(TaskRecordInfo taskRecordInfo) {
        return taskRecordInfoRepository.saveAndFlush(taskRecordInfo);
    }

    /**
     * <pre>
     * 根据主键查找任务记录信息
     * @param primaryKey
     * @return TaskRecordInfo.class
     * </pre>
     */
    @Override
    public TaskRecordInfo findOne(String primaryKey) {
        return taskRecordInfoRepository.findOne(primaryKey);
    }

    /**
     * <pre>
     * 根据任务信息id查找任务记录信息
     * @param taskId
     * @return TaskRecordInfo.class
     * </pre>
     */
    @Override
    public TaskRecordInfo findByTaskId(String taskId) {
        return taskRecordInfoRepository.findByTaskId(taskId);
    }

    /**
     * <pre>
     * 根据用户mid查找任务记录信息
     * @param userId
     * @return TaskRecordInfo.class
     * </pre>
     */
    @Override
    public TaskRecordInfo findByUserId(String userId) {
        return taskRecordInfoRepository.findByUserId(userId);
    }

    /**
     * <pre>
     * 查找任务所有的任务记录信息列表
     * @return List<TaskRecordInfo>.class
     * </pre>
     */
    @Override
    public List<TaskRecordInfo> findAll() {
        return taskRecordInfoRepository.findAll();
    }

    /**
     * <pre>
     * 分页查找任务所有的任务记录信息列表
     * @return Page<TaskRecordInfo>.class
     * </pre>
     *
     * @param pageable
     */
    @Override
    public Page<TaskRecordInfo> findAll(Pageable pageable) {
        return taskRecordInfoRepository.findAll(pageable);
    }
}
