package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.utils.MathUtil;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.*;
import com.potato369.novel.basic.dataobject.IncomeInfo;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.TaskInfo;
import com.potato369.novel.basic.dataobject.TaskRecordInfo;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.basic.enums.TaskTypeEnum;
import com.potato369.novel.basic.service.IncomeInfoService;
import com.potato369.novel.basic.service.TaskInfoService;
import com.potato369.novel.basic.service.TaskRecordInfoService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@SuppressWarnings("unchecked")
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
            Sort sort = new Sort(Direction.ASC, "taskSort");
            PageRequest pageRequest = new PageRequest(page - 1, size, sort);
            List<TaskInfoVO> taskInfoVOList = new ArrayList<TaskInfoVO>();
            Page<TaskInfo> taskInfoPage = taskInfoService.findAll(pageRequest);
            Date date = new Date();
            Date start = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 00:00:00"));
            Date end = DateUtil.dateFormat(DateUtil.sdfTimeFmt, DateUtil.strFormat(date, DateUtil.sdfDayFmt).concat(" 23:59:59"));
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

    @GetMapping(value = "/task/finish.do")
    public void finishTask(@RequestParam(name = "userId") String userId,
                           @RequestParam(name = "taskId") String taskId,
                           @RequestParam(name = "finishedDate") Date finishedDate) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("");
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("");
            }
        }
    }

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
