package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.TaskRecordInfo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @InterfaceName TaskRecordInfoRepository
 * @Desc 任务进度记录信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/13 15:33
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface TaskRecordInfoRepository extends JpaRepository<TaskRecordInfo, String> {

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
     * 根据用户mid查找任务记录信息
     * @param userId
     * @return TaskRecordInfo.class
     * </pre>
     */
    TaskRecordInfo findByUserId(String userId);
    
    
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
    @Query("SELECT tr FROM TaskRecordInfo tr WHERE tr.taskId = ?1 AND tr.userId = ?2 AND tr.finishedTime >= ?3 AND tr.finishedTime <= ?4 ORDER BY tr.finishedTime ASC")
    List<TaskRecordInfo> findByDateTask(String taskId, String userId, Date start, Date end);
    
    /**
     * <pre>
     * 根据任务信息id和用户id查找任务记录信息
     * @param taskId 任务id
     * @param userId 用户id
     * @return
     * </pre>
     */
    @Query("SELECT tr FROM TaskRecordInfo tr WHERE tr.userId = ?1 AND tr.taskId = ?2 ORDER BY tr.finishedTime ASC")
    List<TaskRecordInfo> findByUserIdAndTaskId(String userId, String taskId);
}
