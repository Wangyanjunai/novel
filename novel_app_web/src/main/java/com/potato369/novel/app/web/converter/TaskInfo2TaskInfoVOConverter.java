package com.potato369.novel.app.web.converter;

import com.potato369.novel.app.web.vo.TaskInfoVO;
import com.potato369.novel.basic.dataobject.TaskInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.converter
 * @ClassName TaskInfo2TaskInfoVOConverter
 * @Desc 将TaskInfo对象转换为TaskInfoVO对象转换器
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/11 14:56
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class TaskInfo2TaskInfoVOConverter {

    public static TaskInfoVO convert(TaskInfo taskInfo) {
      TaskInfoVO taskInfoVO = TaskInfoVO.builder().build();
      BeanUtils.copyProperties(taskInfo, taskInfoVO);
      return taskInfoVO;
    }

    public static List<TaskInfoVO> convertToTaskInfoVOList(List<TaskInfo> taskInfoList) {
        return taskInfoList.stream().map(taskInfo -> convert(taskInfo)).collect(Collectors.toList());
    }

    public static TaskInfo convert(TaskInfoVO taskInfoVO) {
        TaskInfo taskInfo = TaskInfo.builder().build();
        BeanUtils.copyProperties(taskInfoVO, taskInfo);
        return taskInfo;
    }

    public static List<TaskInfo> convertToTaskInfoList(List<TaskInfoVO> taskInfoVOList) {
        return taskInfoVOList.stream().map(taskInfoVO -> convert(taskInfoVO)).collect(Collectors.toList());
    }


}
