package com.potato369.novel.basic.dataobject;

import com.potato369.novel.basic.enums.TaskTypeEnum;
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
 * @ClassName TaskRecordInfo
 * @Desc 任务信息记录实体
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/13 15:04
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@AllArgsConstructor
@Builder
@Data
@DynamicUpdate
@Entity(name = "TaskRecordInfo")
@NoArgsConstructor
@Table(name = "task_record_info")
public class TaskRecordInfo implements Serializable{

	/**
	 * <pre>
	 * @serialField serialVersionUID：序列号。
	 * </pre>
	 */
	@Transient
	private static final long serialVersionUID = 5209495091597505999L;
	
	/**
	 * <pre>
	 * @serialField taskRecordId：任务记录id，主键。
	 * </pre>
	 */
	@Id
	@Column(name = "task_record_id", nullable = false, length = 32)
	private String taskRecordId;

	/**
	 * <pre>
	 * @serialField taskId：任务id，主键。
	 * </pre>
	 */
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
	 * @serialField taskStatus：任务完成状态，0-未完成，1-完成，默认：0-未完成。
	 * </pre>
	 */
	@Builder.Default
	@Column(name = "task_status", nullable = false, length = 1)
	private Integer taskStatus = TaskTypeEnum.UNFINISHED.getCode();

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
}
