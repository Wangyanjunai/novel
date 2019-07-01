package com.potato369.novel.app.web.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.vo
 * @ClassName TaskInfoVO
 * @Desc 任务中心数据传输类实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/11 14:52
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class TaskInfoVO {
	
	/**
     * <pre>
     * @JsonProperty taskId：任务id。
     * </pre>
     */
	@JsonProperty(value = "no")
	private String taskId;

    /**
     * <pre>
     * @JsonProperty taskName：任务名称。
     * </pre>
     */
	@JsonProperty(value = "name")
    private String taskName;

    /**
     * <pre>
     * @JsonProperty taskType：任务类型，1-绑定任务；2-分享任务；3-下载任务；4-阅读任务。
     * </pre>
     */
    @JsonProperty(value = "type")
    private Integer taskType;
    
    /**
     * <pre>
     * @JsonProperty isOrNotFinished：是否完成任务。
     * </pre>
     */
    @JsonProperty(value = "isOrNotFinished")
    private Integer isOrNotFinished;

    /**
     * <pre>
     * @JsonProperty taskDescription：任务描述。
     * </pre>
     */
    @JsonProperty(value = "description")
    private String taskDescription;

    /**
     * <pre>
     * @JsonProperty taskProgressValue：任务红包进度条。
     * </pre>
     */
    @JsonProperty(value = "progress")
    private Integer taskProgressValue;
    
    /**
     * <pre>
     * @JsonProperty taskTimes：任务需要的完成次数。
     * </pre>
     */
    @JsonProperty(value = "times")
    private Integer taskTimes;
    
    /**
     * <pre>
     * @JsonProperty hasfinishedTimes：已经完成次数。
     * </pre>
     */
    @JsonProperty(value = "hasfinishedTimes")
    private Integer hasfinishedTimes;

    /**
     * <pre>
     * @JsonProperty finishedTime：完成时间。
     * </pre>
     */
    @JsonProperty(value = "finishedTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishedTime;
}
