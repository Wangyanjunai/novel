package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.TaskTypeEnum;
import com.potato369.novel.basic.utils.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @ClassName TaskInfo
 * @Desc 任务信息记录实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 15:04
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "TaskInfo")
@NoArgsConstructor
@Table(name = "task_info")
public class TaskInfo implements Serializable {

    /**
     * <pre>
     * @serialField serialVersionUID：序列号。
     * </pre>
     */
    @Transient
    private static final long serialVersionUID = 5209495091597505999L;

    /**
     * <pre>
     * @serialField taskId：任务id，主键。
     * </pre>
     */
    @Id
    @Column(name = "task_id", nullable = false, length = 32)
    private String taskId;

    /**
     * <pre>
     * @serialField userId：用户mid。
     * </pre>
     */
    @Column(name = "user_id", nullable = false, length = 20)
    private String userId;

    /**
     * <pre>
     * @serialField taskName：任务名称。
     * </pre>
     */
    @Column(name = "task_name", nullable = false, length = 64)
    private String taskName;

    /**
     * <pre>
     * @serialField taskType：任务类型，1-绑定任务；2-分享任务；3-下载任务；4-阅读任务。
     * </pre>
     */
    @Builder.Default
    @Column(name = "task_type", nullable = false, length = 1)
    private Integer taskType = TaskTypeEnum.BINDING.getCode();

    /**
     * <pre>
     * @serialField taskName：任务描述。
     * </pre>
     */
    @Column(name = "task_description", length = 1024)
    private String taskDescription;

    /**
     * <pre>
     * @serialField taskProgressValue：任务红包进度条。
     * </pre>
     */
    @Builder.Default
    @Column(name = "task_progress_value", length = 2)
    private Integer taskProgressValue = Integer.valueOf(0);

    /**
     * <pre>
     * @serialField createTime：创建时间。
     * </pre>
     */
    @Column(name = "create_time", nullable = false, length = 64)
    private Date createTime;

    /**
     * <pre>
     * @serialField updateTime：更新时间。
     * </pre>
     */
    @Column(name = "update_time", nullable = false, length = 64)
    private Date updateTime;

    @Transient
    public TaskTypeEnum getTaskTypeEnum() {
        return EnumUtil.getByCode(taskType, TaskTypeEnum.class);
    }
}
