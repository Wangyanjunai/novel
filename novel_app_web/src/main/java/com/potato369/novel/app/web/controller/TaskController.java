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
import com.potato369.novel.basic.enums.UserInfoUserTypeEnum;
import com.potato369.novel.basic.service.IncomeInfoService;
import com.potato369.novel.basic.service.TaskInfoService;
import com.potato369.novel.basic.service.TaskRecordInfoService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResultVO<TaskVO> list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size,
                                 @RequestParam(name = "userId") String userId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("start==================后端查询任务信息列表==================start");
            }
            ResultVO<TaskVO> resultVO = new ResultVO<>();
            TaskVO taskVO = TaskVO.builder().build();
            Sort sort = new Sort(Direction.ASC, "taskSort", "createTime");
            PageRequest pageRequest = new PageRequest(page - 1, size, sort);
            List<TaskInfoVO> taskInfoVOList = new ArrayList<>();
            Page<TaskInfo> taskInfoPage = taskInfoService.findAll(pageRequest);
            Date date = new Date();//今天的时间
            Date start = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 00:00:00"));
            Date end = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 23:59:59"));
            NovelUserInfo userInfo = userInfoService.findByUserMId(userId);
            for (TaskInfo taskInfo : taskInfoPage.getContent()) {
                String taskId = taskInfo.getTaskId();
                TaskInfoVO taskInfoVO = TaskInfoVO.builder().build();
                List<TaskRecordInfo> taskRecordList = taskRecordInfoService.findByDateTask(taskId, userId, start, end);
                if (taskRecordList != null && taskRecordList.isEmpty() && taskRecordList.size() > 0) {
                    Integer taskFinished = 0;
                    for (TaskRecordInfo taskRecordInfo : taskRecordList) {
                        taskFinished += taskRecordInfo.getTaskFinishedTimes();
                        if (TaskTypeEnum.FINISHED.getCode().equals(taskRecordInfo.getTaskStatus())) {
                            if (taskFinished.equals(taskInfo.getTaskTimes())) {
                                taskInfoVO.setFinishedTime(taskRecordInfo.getFinishedTime());
                                taskInfoVO.setIsOrNotFinished(TaskTypeEnum.FINISHED.getCode());
                            }
                        }
                        taskInfoVO.setHasfinishedTimes(taskFinished);
                    }
                } else {
                    taskInfoVO.setFinishedTime(null);
                    taskInfoVO.setIsOrNotFinished(TaskTypeEnum.UNFINISHED.getCode());
                    taskInfoVO.setHasfinishedTimes(0);
                }
                taskInfoVO.setTaskDescription(taskInfo.getTaskDescription());
                taskInfoVO.setTaskId(taskInfo.getTaskId());
                taskInfoVO.setTaskName(taskInfo.getTaskName());
                taskInfoVO.setTaskProgressValue(taskInfo.getTaskProgressValue());
                taskInfoVO.setTaskTimes(taskInfo.getTaskTimes());
                taskInfoVO.setTaskType(taskInfo.getTaskType());
                taskInfoVO.setUserId(userId);
                taskInfoVOList.add(taskInfoVO);
            }
            taskVO.setTaskInfoVOList(taskInfoVOList);
            taskVO.setTotalPage(new BigDecimal(taskInfoPage.getTotalPages()));
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
                log.error("完成某个特定的任务需要给对应的用户添加红包进度值，用户信息不存在。");
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
            if (TaskTypeEnum.BINDING.getCode().equals(taskInfo.getTaskType())) {
                if (UserInfoIsOrNotBandWechatEnum.FINISHED.getCode().equals(userInfo.getIsOrNotBandWechat()) && StringUtils.isNotEmpty(userInfo.getBindWeChatOpenid())) {
                    log.error("完成某个特定的任务需要给对应的用户添加红包进度值，用户已经完成绑定微信任务。");
                    resultVO.setCode(0);
                    resultVO.setMsg("用户已经完成绑定微信任务");
                    return resultVO;
                } else {
                    if (UserInfoUserTypeEnum.VISITOR.getCode().equals(userInfo.getUserType())) {
                        userInfo.setOpenid(taskInfoDTO.getOpenid());
                        userInfo.setAddress(taskInfoDTO.getAddress());
                        userInfo.setAvatarUrl(taskInfoDTO.getAvatarUrl());
                        userInfo.setGender(taskInfoDTO.getGender());
                        userInfo.setNickName(taskInfoDTO.getNickName());
                    }
                }
            }
            userInfo.setEnvelopeAmount(userInfo.getEnvelopeAmount().add(BigDecimal.valueOf(taskInfo.getTaskProgressValue())));
            userInfo.setBindWeChatOpenid(taskInfoDTO.getOpenid());
            userInfo.setIsOrNotBandWechat(UserInfoIsOrNotBandWechatEnum.FINISHED.getCode());
            userInfoService.update(userInfo);
            Date finishedDate = DateUtil.dateFormat(DateUtil.sdfTimeFmt, taskInfoDTO.getFinishedDateString());
            if (finishedDate == null) {
                log.error("完成某个特定的任务需要给对应的用户添加红包进度值，完成时间参数格式不正确。");
                throw new Exception(ResultEnum.PARAM_ERROR.getMessage());
            }
            TaskRecordInfo taskRecordInfo = TaskRecordInfo.builder().build();
            taskRecordInfo.setTaskId(taskInfo.getTaskId());
            taskRecordInfo.setUserId(taskInfoDTO.getMId());
            taskRecordInfo.setTaskRecordId(UUIDUtil.gen32UUID());
            if (taskInfo.getTaskTimes() == taskInfoDTO.getTimes()) {
                taskRecordInfo.setTaskFinishedTimes(taskInfoDTO.getTimes());
                taskRecordInfo.setFinishedTime(finishedDate);
            }
            taskRecordInfoService.save(taskRecordInfo);
            BalanceVO balanceVO = BalanceVO.builder().build();
            balanceVO.setUserId(userInfo.getMId());
            balanceVO.setBalanceAmount(userInfo.getBalanceAmount());
            balanceVO.setEnvelopeAmount(userInfo.getEnvelopeAmount());
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
