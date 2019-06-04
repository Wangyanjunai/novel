package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.TaskInfo;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName TaskInfoService
 * @Desc 任务进度Service
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
}
