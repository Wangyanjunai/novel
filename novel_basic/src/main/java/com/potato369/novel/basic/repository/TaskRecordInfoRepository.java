package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.TaskRecordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
