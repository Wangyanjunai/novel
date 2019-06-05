package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.TaskInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName TaskInfoService
 * @Desc 任务进度Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 18:31
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface TaskInfoService {

    /**
     * <pre>
     * 新增任务进度记录信息
     * @param taskInfo
     * @return TaskInfo
     * </pre>
     */
    TaskInfo save(TaskInfo taskInfo);

    /**
     * <pre>
     * 根据主键删除任务进度记录信息
     * @param primaryKey
     * </pre>
     */
    void delete(String primaryKey);

    /**
     * <pre>
     * 修改任务进度记录信息
     * @param taskInfo
     * @return TaskInfo
     * </pre>
     */
    TaskInfo update(TaskInfo taskInfo);

    /**
     * <pre>
     * 根据主键查找一个任务进度记录信息
     * @param primaryKey
     * @return TaskInfo
     * </pre>
     */
    TaskInfo findOne(String primaryKey);

    /**
     * <pre>
     * 查找任务进度记录信息列表
     * @return List<TaskInfo>
     * </pre>
     */
    List<TaskInfo> findAll();

    /**
     * <pre>
     * 分页查询任务进度记录信息列表
     * @param pageable
     * @return Page<TaskInfo>
     * </pre>
     */
    Page<TaskInfo> findAll(Pageable pageable);
}
