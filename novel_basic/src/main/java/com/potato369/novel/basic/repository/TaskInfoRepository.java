package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.TaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @InterfaceName TaskInfoRepository
 * @Desc 任务进度信息repository
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 17:24
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface TaskInfoRepository extends JpaRepository<TaskInfo, String> {
}
