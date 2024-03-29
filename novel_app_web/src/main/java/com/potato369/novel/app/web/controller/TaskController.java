package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.dto.TaskInfoDTO;
import com.potato369.novel.app.web.utils.MathUtil;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.*;
import com.potato369.novel.basic.dataobject.IncomeInfo;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.TaskInfo;
import com.potato369.novel.basic.dataobject.TaskRecordInfo;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.basic.enums.TaskTypeEnum;
import com.potato369.novel.basic.enums.UserInfoIsOrNotBandWechatEnum;
import com.potato369.novel.basic.service.IncomeInfoService;
import com.potato369.novel.basic.service.TaskInfoService;
import com.potato369.novel.basic.service.TaskRecordInfoService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * @ClassName TaskController
 * @Function 任务中心控制器
 * @Reason 任务Controller
 * @Date 2019年6月11日 上午11:17:32
 * @Desc 提供前端任务数据列表或者用户完成任务信息记录
 * @author 王艳军
 * @version 1.0
 * @since JDK 1.6
 * <pre>
 */
@RestController
@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public class TaskController {

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private TaskRecordInfoService taskRecordInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private IncomeInfoService incomeInfoService;

    /**
     * <pre>
     * 任务列表数据
     * </pre>
     */
    @GetMapping(value = "/task/list.do")
    public ResultVO<TaskVO> list(@RequestParam(name = "userId", required = false) String userId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("start==================后端查询任务信息列表==================start");
            }
            ResultVO<TaskVO> resultVO = new ResultVO<>();
            TaskVO taskVO = TaskVO.builder().build();
            Sort sort = new Sort(Direction.ASC, "taskSort", "createTime", "updateTime");
            List<TaskInfo> taskInfoList = taskInfoService.findAll(sort);
            Date date  = new Date();// 今天的时间
            Date start = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 00:00:00"));
            Date end   = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 23:59:59"));
            List<TaskInfoVO> taskInfoVOList = new ArrayList<>();
            NovelUserInfo novelUserInfo =null;
            if (StringUtils.isNoneEmpty(userId)) {
            	novelUserInfo = userInfoService.findById(userId);
			}
            if (novelUserInfo != null) {
            	for (TaskInfo taskInfo : taskInfoList) {
                    String taskId = taskInfo.getTaskId();
                    TaskInfoVO taskInfoVO = TaskInfoVO.builder().build();
                    if (TaskTypeEnum.BINDING.getCode().equals(taskInfo.getTaskType())) {
                    	List<TaskRecordInfo> taskRecordList1 = taskRecordInfoService.findByUserIdAndTaskId(userId, taskId);
                    	for (TaskRecordInfo taskRecordInfo : taskRecordList1) {
    						if (TaskTypeEnum.FINISHED.getCode().equals(taskRecordInfo.getTaskStatus()) 
    							&& taskRecordInfo.getTaskFinishedTimes().equals(taskInfo.getTaskTimes())
    							&& StringUtils.isNotEmpty(novelUserInfo.getBindWeChatOpenid())) {
    							taskInfoVO.setFinishedTime(taskRecordInfo.getFinishedTime());// 设置任务的完成时间
                                taskInfoVO.setIsOrNotFinished(TaskTypeEnum.FINISHED.getCode());// 设置任务的完成状态
                                taskInfoVO.setHasfinishedTimes(taskRecordInfo.getTaskFinishedTimes());// 设置已经完成任务的次数
    						}
    					}
    				} else {
    					List<TaskRecordInfo> taskRecordList = taskRecordInfoService.findByDateTask(taskId, userId, start, end);
    	                if (taskRecordList != null && !taskRecordList.isEmpty() && taskRecordList.size() > 0) {
    	                    Integer taskFinished = 0;
    	                    for (TaskRecordInfo taskRecordInfo : taskRecordList) {
    	                        taskFinished += taskRecordInfo.getTaskFinishedTimes();
    	                        if (TaskTypeEnum.FINISHED.getCode().equals(taskRecordInfo.getTaskStatus())) {
    	                            if (taskFinished.equals(taskInfo.getTaskTimes())) {
    	                                taskInfoVO.setFinishedTime(taskRecordInfo.getFinishedTime());// 设置任务的完成时间
    	                                taskInfoVO.setIsOrNotFinished(TaskTypeEnum.FINISHED.getCode());// 设置任务的完成状态
    	                            }
    	                        }
    	                    }
    	                    taskInfoVO.setHasfinishedTimes(taskFinished);// 设置已经完成任务的次数
    	                } else {
    	                	taskInfoVO.setFinishedTime(null);// 设置任务的完成时间
    	                    taskInfoVO.setIsOrNotFinished(TaskTypeEnum.UNFINISHED.getCode());// 设置任务的完成状态
    	                    taskInfoVO.setHasfinishedTimes(Integer.valueOf(0));// 设置已经完成任务的次数
    	                }
    				}
                    taskInfoVO.setTaskDescription(taskInfo.getTaskDescription());
                    taskInfoVO.setTaskId(taskInfo.getTaskId());
                    taskInfoVO.setTaskName(taskInfo.getTaskName());
                    taskInfoVO.setTaskProgressValue(taskInfo.getTaskProgressValue());
                    taskInfoVO.setTaskTimes(taskInfo.getTaskTimes());
                    taskInfoVO.setTaskType(taskInfo.getTaskType());
                    taskInfoVOList.add(taskInfoVO);
                }
			} else {
				for (TaskInfo taskInfo : taskInfoList) {
                    TaskInfoVO taskInfoVO = TaskInfoVO.builder().build();
                    taskInfoVO.setFinishedTime(null);// 设置任务的完成时间
                    taskInfoVO.setIsOrNotFinished(TaskTypeEnum.UNFINISHED.getCode());// 设置任务的完成状态
                    taskInfoVO.setHasfinishedTimes(Integer.valueOf(0));// 设置已经完成任务的次数
                    taskInfoVO.setTaskDescription(taskInfo.getTaskDescription());
                    taskInfoVO.setTaskId(taskInfo.getTaskId());
                    taskInfoVO.setTaskName(taskInfo.getTaskName());
                    taskInfoVO.setTaskProgressValue(taskInfo.getTaskProgressValue());
                    taskInfoVO.setTaskTimes(taskInfo.getTaskTimes());
                    taskInfoVO.setTaskType(taskInfo.getTaskType());
                    taskInfoVOList.add(taskInfoVO);
                }
			}
            taskVO.setTaskInfoVOList(taskInfoVOList);
            taskVO.setTotalPage(new BigDecimal(taskInfoList.size()));
            resultVO.setData(taskVO);
            resultVO.setCode(0);
            resultVO.setMsg("返回数据成功");
            return resultVO;
        } catch (Exception e) {
            log.error("后端查询任务信息列表失败", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end====================后端查询任务信息列表====================end");
            }
        }
    }

    /**
     * <pre>
     * finishTask：完成某个特定的任务需要给对应的用户添加红包进度值。
     * @param taskInfoDTO
     * @param bindingResult
     * </pre>
     */

    @PostMapping(value = "/task/finish.do", produces = "application/json;charset=utf-8")
    public ResultVO finishTask(@RequestBody @Valid TaskInfoDTO taskInfoDTO, BindingResult bindingResult) {
        ResultVO resultVO = new ResultVO();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================完成某个特定的任务需要给对应的用户添加红包进度值====================start");
                log.debug("完成某个特定的任务，用户信息={}", taskInfoDTO);
            }
            if (bindingResult.hasErrors()) {
                resultVO.setMsg(bindingResult.getFieldError().getDefaultMessage());
                resultVO.setCode(-1);
                return resultVO;
            }
            if (taskInfoDTO == null) {
                log.error("完成某个特定的任务需要给对应的用户添加红包进度值，前端传过来的信息不存在。");
                throw new Exception(ResultEnum.MP_USER_INFO_EMPTY.getMessage());
            }
            NovelUserInfo userInfo = userInfoService.findByUserMId(taskInfoDTO.getMId());
            if (userInfo == null) {
                log.error("完成某个特定的任务需要给对应的用户添加红包进度值，用户信息不存在。");
                throw new Exception(ResultEnum.MP_USER_INFO_EMPTY.getMessage());
            }
            TaskInfo taskInfo = taskInfoService.findOne(taskInfoDTO.getTaskId());
            if (taskInfo == null) {
                log.error("完成某个特定的任务需要给对应的用户添加红包进度值，任务信息不存在。");
                throw new Exception(ResultEnum.TASK_INFO_EMPTY.getMessage());
            }
            NovelUserInfo updateInfo = userInfo;
            Integer isOrNotFinished = TaskTypeEnum.UNFINISHED.getCode();
            Integer hasfinishedTimes = 0;
            if (TaskTypeEnum.BINDING.getCode().equals(taskInfo.getTaskType())) {
            	List<TaskRecordInfo> taskRecordList1 = taskRecordInfoService.findByUserIdAndTaskId(userInfo.getMId(), taskInfo.getTaskId());
            	if (taskRecordList1 != null && !taskRecordList1.isEmpty() && taskRecordList1.size() > 0) {
            		if (taskRecordList1.size() >= taskInfo.getTaskTimes()) {
                        isOrNotFinished = TaskTypeEnum.FINISHED.getCode();
                        hasfinishedTimes = 1;
            			log.error("用户已经完成绑定微信任务，任务id={}，用户mid={}", taskInfo.getTaskId(), userInfo.getMId());
						throw new Exception("用户已经完成绑定微信任务");
					}
            	} else {
            		userInfo.setEnvelopeAmount(userInfo.getEnvelopeAmount().add(BigDecimal.valueOf(taskInfo.getTaskProgressValue())));
            		userInfo.setBindWeChatOpenid(taskInfoDTO.getOpenid());
            		userInfo.setIsOrNotBandWechat(UserInfoIsOrNotBandWechatEnum.FINISHED.getCode());
            		userInfo.setBalanceAmount(userInfo.getBalanceAmount().add(taskInfo.getProfitAmount()));
            		updateInfo = userInfoService.update(userInfo);
            		TaskRecordInfo taskRecordInfo = TaskRecordInfo.builder().build();
                    taskRecordInfo.setTaskId(taskInfo.getTaskId());
                    taskRecordInfo.setUserId(taskInfoDTO.getMId());
                    taskRecordInfo.setTaskRecordId(UUIDUtil.gen32UUID());
                    taskRecordInfo.setTaskFinishedTimes(1);
                    taskRecordInfo.setFinishedTime(new Date());
                    taskRecordInfo.setTaskStatus(TaskTypeEnum.FINISHED.getCode());
                    taskRecordInfoService.save(taskRecordInfo);
                    IncomeInfo incomeInfo = IncomeInfo.builder().build();
                    incomeInfo.setIncomeId(UUIDUtil.gen32UUID());
                    incomeInfo.setIncomeTime(new Date());
                    incomeInfo.setUserId(updateInfo.getMId());
                    incomeInfo.setIncomeAmount(taskInfo.getProfitAmount());
                    incomeInfoService.save(incomeInfo);
                    isOrNotFinished = TaskTypeEnum.FINISHED.getCode();
                    hasfinishedTimes = 1;
				}
			} else {
				Date date  = new Date();// 今天的时间
	            Date start = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 00:00:00"));
	            Date end   = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 23:59:59"));
	    		List<TaskRecordInfo> taskRecordList2 = taskRecordInfoService.findByDateTask(taskInfo.getTaskId(), userInfo.getMId(), start, end);
				if (taskRecordList2.size() >= taskInfo.getTaskTimes()) {
                    isOrNotFinished = TaskTypeEnum.FINISHED.getCode();
                    hasfinishedTimes = taskInfo.getTaskTimes();
				    log.error("用户已经完成当天date={}特定的任务，任务id={}，用户mid={}",DateUtil.strFormat(date, DateUtil.sdfTimeCNFmt), taskInfo.getTaskId(), userInfo.getMId());
					throw new Exception("用户已经完成当天特定的任务");
				} else {
					userInfo.setEnvelopeAmount(userInfo.getEnvelopeAmount().add(BigDecimal.valueOf(taskInfo.getTaskProgressValue())));
					userInfo.setBalanceAmount(userInfo.getBalanceAmount().add(taskInfo.getProfitAmount()));
					updateInfo = userInfoService.update(userInfo);
		            TaskRecordInfo taskRecordInfo = TaskRecordInfo.builder().build();
		            taskRecordInfo.setTaskId(taskInfo.getTaskId());
		            taskRecordInfo.setUserId(taskInfoDTO.getMId());
		            taskRecordInfo.setTaskRecordId(UUIDUtil.gen32UUID());
		            taskRecordInfo.setTaskStatus(TaskTypeEnum.FINISHED.getCode());
		            taskRecordInfo.setTaskFinishedTimes(1);
		            taskRecordInfo.setFinishedTime(new Date());
		            taskRecordInfoService.save(taskRecordInfo);
                    List<TaskRecordInfo> taskRecordList3 = taskRecordInfoService.findByDateTask(taskInfo.getTaskId(), userInfo.getMId(), start, end);
                    if (taskRecordList3 != null && !taskRecordList3.isEmpty() && taskRecordList3.size() > 0) {
                        if (taskRecordList3.size() >= taskInfo.getTaskTimes()) {
                            isOrNotFinished = TaskTypeEnum.FINISHED.getCode();
                            hasfinishedTimes = taskInfo.getTaskTimes();
                        } else {
                        	isOrNotFinished = TaskTypeEnum.UNFINISHED.getCode();
                            hasfinishedTimes = taskInfo.getTaskTimes();
						}
                    }

				}
			}
            Date date = new Date();
            Date start = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 00:00:00"));
            Date end = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(DateUtil.getAfterDayDate(date, 7), DateUtil.sdfDayFmt).concat(" 23:59:59"));
            if (log.isDebugEnabled()) {
                log.debug("start date = {}, end date = {}", DateUtil.strFormat(start, DateUtil.sdfTimeCNFmt), DateUtil.strFormat(end, DateUtil.sdfTimeCNFmt));
            }
            BigDecimal yieldAmount = incomeInfoService.get7DaysIncomeAmount(userInfo.getMId(), start, end);
            BalanceVO balanceVO = BalanceVO.builder().build();
            balanceVO.setUserId(updateInfo.getMId());
            balanceVO.setBalanceAmount(updateInfo.getBalanceAmount());
            balanceVO.setEnvelopeAmount(updateInfo.getEnvelopeAmount());
            balanceVO.setYieldAmount(yieldAmount);
            balanceVO.setIsOrNotFinished(isOrNotFinished);
            balanceVO.setHasfinishedTimes(hasfinishedTimes);
            resultVO.setMsg("任务完成成功");
            resultVO.setCode(0);
            resultVO.setData(balanceVO);
            return resultVO;
        } catch (Exception e) {
            log.error("完成某个特定的任务需要给对应的用户添加红包进度值出现错误", e);
            resultVO.setCode(-1);
            resultVO.setMsg("任务完成成功出错");
            return resultVO;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================完成某个特定的任务需要给对应的用户添加红包进度值======================end");
            }
        }
    }

    /**
     * <pre>
     * findBalance：获取任务中心用户的余额，最近7天的收益和红包进度值
     * @param userId
     * @return
     * </pre>
     */
    @GetMapping(value = "/balance/find.do")
    public ResultVO<BalanceVO> findBalance(@RequestParam(name = "userId") String userId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================任务中心查询我的余额和最近7天收益====================start");
            }
            ResultVO<BalanceVO> resultVO = new ResultVO<>();
            BalanceVO balanceVO = BalanceVO.builder().build();
            NovelUserInfo userInfo = userInfoService.findByUserMId(userId);
            if (userInfo == null) {
                log.error("任务中心查询我的余额和最近7天收益，用户信息不存在在。");
                throw new Exception(ResultEnum.MP_USER_INFO_EMPTY.getMessage());
            }
            Date date = new Date();
            Date start = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 00:00:00"));
            Date end = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(DateUtil.getAfterDayDate(date, 7), DateUtil.sdfDayFmt).concat(" 23:59:59"));
            if (log.isDebugEnabled()) {
                log.debug("start date = {}, end date = {}", DateUtil.strFormat(start, DateUtil.sdfTimeCNFmt), DateUtil.strFormat(end, DateUtil.sdfTimeCNFmt));
            }
            BigDecimal yieldAmount = incomeInfoService.get7DaysIncomeAmount(userInfo.getMId(), start, end);
            resultVO.setMsg("返回数据成功");
            resultVO.setCode(0);
            balanceVO.setBalanceAmount(userInfo.getBalanceAmount());
            balanceVO.setEnvelopeAmount(userInfo.getEnvelopeAmount());
            balanceVO.setYieldAmount(yieldAmount);
            balanceVO.setUserId(userId);
            resultVO.setData(balanceVO);
            return resultVO;
        } catch (Exception e) {
            log.error("任务中心查询我的余额和最近7天收益出现错误", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================任务中心查询我的余额和最近7天收益======================end");
            }
        }
    }

    /**
     * <pre>
     * addEnvelope方法的作用：添加用户红包进度值。
     * @param userId
     * @param envelope
     * </pre>
     */
    @GetMapping(value = "/envelope/add.do")
    public ResultVO<EnvelopeVO> addEnvelope(@RequestParam(name = "userId") String userId,
                                            @RequestParam(name = "envelope") BigDecimal envelope) {
        ResultVO<EnvelopeVO> resultVO = new ResultVO<>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start======================添加用户红包进度值======================start");
            }
            if (log.isDebugEnabled()) {
                log.debug("添加用户红包进度值，用户mid={}，红包进度值={}", userId, envelope);
            }
            NovelUserInfo userInfo = userInfoService.findById(userId);
            if (userInfo == null) {
                log.error("添加用户红包进度值，用户信息不存在。");
                throw new Exception(ResultEnum.MP_USER_INFO_EMPTY.getMessage());
            }
            if (log.isDebugEnabled()) {
                log.info("更新前，userInfo={}", userInfo);
            }
            userInfo.setEnvelopeAmount(userInfo.getEnvelopeAmount().add(envelope));
            userInfoService.update(userInfo);
            if (log.isDebugEnabled()) {
                log.debug("更新后，userInfo={}", userInfo);
            }
            EnvelopeVO envelopeVO = EnvelopeVO.builder().build();
            envelopeVO.setBalanceAmount(userInfo.getBalanceAmount());
            envelopeVO.setEnvelopeAmount(userInfo.getEnvelopeAmount());
            envelopeVO.setUserId(userInfo.getMId());
            resultVO.setCode(0);
            resultVO.setData(envelopeVO);
            resultVO.setMsg("添加红包进度值成功");
            return resultVO;
        } catch (Exception e) {
            log.error("添加用户红包进度值出现错误", e);
            resultVO.setCode(-1);
            resultVO.setMsg("添加红包进度值失败");
            return resultVO;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end========================添加用户红包进度值========================end");
            }
        }
    }
    /*
    /**
     * <pre>
     * findEnvelope方法的作用：获取用户红包进度值。
     * @param userId 用户mid。
     * </pre>
     */
    /*
    @GetMapping(value = "/envelope/find.do")
    public ResultVO<EnvelopeVO> findEnvelope(@RequestParam(name = "userId") String userId) {
    	ResultVO<EnvelopeVO> resultVO = new ResultVO<>();
    	try {
    		if (log.isDebugEnabled()) {
				log.debug("start======================获取用户红包进度值======================start");
			}
        	if (log.isDebugEnabled()) {
				log.debug("获取用户红包进度值，用户mid={}", userId);
			}
        	NovelUserInfo userInfo = userInfoService.findById(userId);
        	if (userInfo == null) {
				log.error("获取用户红包进度值，用户信息不存在。");
				throw new Exception(ResultEnum.MP_USER_INFO_EMPTY.getMessage());
			}
        	EnvelopeVO envelopeVO = EnvelopeVO.builder().build();
        	envelopeVO.setUserId(userInfo.getMId());
        	envelopeVO.setBalanceAmount(userInfo.getBalanceAmount());
        	envelopeVO.setEnvelopeAmount(userInfo.getEnvelopeAmount());
        	resultVO.setCode(0);
        	resultVO.setData(envelopeVO);
        	resultVO.setMsg("返回数据成功");
        	return resultVO;
        } catch (Exception e) {
        	log.error("获取用户红包进度值出现错误", e);
        	resultVO.setCode(-1);
        	resultVO.setMsg(e.getMessage());
        	return resultVO;
        } finally {
        	if (log.isDebugEnabled()) {
				log.debug("end========================获取用户红包进度值========================end");
			}
        }
    }
    */

    /**
     * <pre>
     * consumeEnvelope方法的作用：消费用户红包进度值，领取随机获得0.1-0.5元提现金额（真随机）。
     * @param userId 用户mid。
     * @param envelope 红包值。
     * </pre>
     */
    @GetMapping(value = "/envelope/consume.do")
    public ResultVO<EnvelopeVO> consumeEnvelope(@RequestParam(name = "userId") String userId,
                                                @RequestParam(name = "envelope") BigDecimal envelope) {
        ResultVO<EnvelopeVO> resultVO = new ResultVO<>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start======================用户消费红包进度值======================start");
            }
            if (log.isDebugEnabled()) {
                log.debug("用户消费红包进度值，用户mid={}，红包进度值={}", userId, envelope);
            }
            NovelUserInfo userInfo = userInfoService.findById(userId);
            if (userInfo == null) {
                log.error("用户消费红包进度值，用户信息不存在。");
                throw new Exception(ResultEnum.MP_USER_INFO_EMPTY.getMessage());
            }
            if (log.isDebugEnabled()) {
                log.debug("更新前，userInfo={}", userInfo);
            }
            if (!MathUtil.compareTo(userInfo.getEnvelopeAmount().doubleValue(), envelope.doubleValue())) {
                log.error("用户消费红包进度值，红包进度条总额不足。");
                throw new Exception(ResultEnum.ENVELOPE_DEFICIENCY.getMessage());
            }
            double balance = MathUtil.getRandom(4);
            BigDecimal envelopeBalance = BigDecimal.valueOf(balance);
            userInfo.setBalanceAmount(userInfo.getBalanceAmount().add(envelopeBalance));
            userInfo.setEnvelopeAmount(userInfo.getEnvelopeAmount().subtract(envelope));
            userInfoService.update(userInfo);
            if (log.isDebugEnabled()) {
                log.debug("更新后，userInfo={}", userInfo);
            }
            IncomeInfo incomeInfo = IncomeInfo.builder().build();
            incomeInfo.setIncomeId(UUIDUtil.gen32UUID());
            incomeInfo.setUserId(userInfo.getMId());
            incomeInfo.setIncomeAmount(envelopeBalance);
            incomeInfoService.save(incomeInfo);
            EnvelopeVO envelopeVO = EnvelopeVO.builder().build();
            envelopeVO.setUserId(userId);
            envelopeVO.setBalanceAmount(userInfo.getBalanceAmount());
            envelopeVO.setEnvelopeAmount(userInfo.getEnvelopeAmount());
            envelopeVO.setRandomEnvelope(envelopeBalance);
            resultVO.setCode(0);
            resultVO.setData(envelopeVO);
            resultVO.setMsg("返回数据成功");
            return resultVO;
        } catch (Exception e) {
            log.error("用户消费红包进度值出现错误", e);
            resultVO.setCode(-1);
            resultVO.setMsg(e.getMessage());
            return resultVO;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end========================用户消费红包进度值========================end");
            }
        }
    }
}
