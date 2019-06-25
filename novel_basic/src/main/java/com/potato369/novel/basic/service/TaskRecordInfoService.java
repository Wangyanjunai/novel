package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.TaskRecordInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName TaskRecordInfoService
 * @Desc 任务进度记录Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/13 15:34
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface TaskRecordInfoService {

    /**
     * <pre>
     * 新增任务记录信息
     * @param taskRecordInfo
     * @return TaskRecordInfo.class
     * </pre>
     */
    TaskRecordInfo save(TaskRecordInfo taskRecordInfo);

    /**
     * <pre>
     * 根据主键删除任务记录信息
     * @param primaryKey
     * @return
     * </pre>
     */
    void delete(String primaryKey);

    /**
     * <pre>
     * 修改任务记录信息
     * @param taskRecordInfo
     * @return TaskRecordInfo.class
     * </pre>
     */
    TaskRecordInfo update(TaskRecordInfo taskRecordInfo);

    /**
     * <pre>
     * 根据主键查找任务记录信息
     * @param primaryKey
     * @return TaskRecordInfo.class
     * </pre>
     */
    TaskRecordInfo findOne(String primaryKey);

    /**
     * <pre>
     * 根据任务信息id查找任务记录信息
     * @param taskId
     * @return TaskRecordInfo.class
     * </pre>
     */
    TaskRecordInfo findByTaskId(String taskId);
    
    /**
     * <pre>
     * 根据任务信息id，用户id，今天的时间，查找任务记录信息，
     * 查询用户某天任务是否完成
     * @param taskId 任务id
     * @param userId 用户id
     * @param start 任务完成开始查询时间
     * @param end 任务完成结束查询时间
     * @return
     * </pre>
     */
    List<TaskRecordInfo> findByDateTask(String taskId, String userId, Date start, Date end);

    /**
     * <pre>
     * 根据用户mid查找任务记录信息
     * @param userId
     * @return TaskRecordInfo.class
     * </pre>
     */
    TaskRecordInfo findByUserId(String userId);

    /**
     * <pre>
     * 查找任务所有的任务记录信息列表
     * @return List<TaskRecordInfo>.class
     * </pre>
     */
    List<TaskRecordInfo> findAll();

    /**
     * <pre>
     * 分页查找任务所有的任务记录信息列表
     * @return Page<TaskRecordInfo>.class
     * </pre>
     */
    Page<TaskRecordInfo> findAll(Pageable pageable);
}
