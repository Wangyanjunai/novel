package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.converter.TaskInfo2TaskInfoVOConverter;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.BalanceVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.app.web.vo.TaskInfoVO;
import com.potato369.novel.app.web.vo.TaskVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.TaskInfo;
import com.potato369.novel.basic.service.TaskInfoService;
import com.potato369.novel.basic.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
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
@RequestMapping("/task")
@Slf4j
@SuppressWarnings("unchecked")
public class TaskController {

    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * <pre>
     * 任务列表数据
     * </pre>
     */
    @GetMapping(value = "/list")
    public ResultVO<TaskVO> list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {

        try {
            if (log.isDebugEnabled()) {
                log.debug("start==================后端查询任务信息列表==================start");
            }
            ResultVO<TaskVO> resultVO = new ResultVO<>();
            TaskVO taskVO = TaskVO.builder().build();
            Sort sort = new Sort(Direction.ASC, "createTime", "updateTime");
            PageRequest pageRequest = new PageRequest(page - 1, size, sort);
            Page<TaskInfo> taskInfoPage = taskInfoService.findAll(pageRequest);
            List<TaskInfoVO> taskInfoVOList = TaskInfo2TaskInfoVOConverter.convertToTaskInfoVOList(taskInfoPage.getContent());
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

    @GetMapping(value = "/balance")
    public ResultVO<BalanceVO> getBalance(@RequestParam(name = "userId") String userId) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================任务中心查询我的余额和最近7天收益====================start");
            }
            ResultVO<BalanceVO> resultVO = new ResultVO<>();
            BalanceVO balanceVO = BalanceVO.builder().build();
            NovelUserInfo userInfo = userInfoService.findByUserMId(userId);
            if (userInfo == null) {
                resultVO.setMsg("返回数据失败，用户信息不存在");
                resultVO.setCode(-2);
                resultVO.setData(null);
            } else {
                resultVO.setMsg("返回数据成功，用户信息存在");
                resultVO.setCode(0);
                balanceVO.setBalanceAmount(userInfo.getBalanceAmount());
                balanceVO.setYieldAmount(BigDecimal.ZERO);
                balanceVO.setUserId(userId);
                resultVO.setData(balanceVO);
            }
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
     * addEnvelopeAmount方法的作用：添加用户红包进度值。
     * </pre>
     */
    @GetMapping(value = "/envelope/add.do")
    public void addEnvelope(@RequestParam(name = "userId") String userId,
                            @RequestParam(name = "envelopeAmount") BigDecimal envelopeAmount) {
        try {

        } catch (Exception e) {

        } finally {

        }
    }

    /**
     * <pre>
     * findEnvelopeAmount方法的作用：获取用户红包进度值。
     * @param userId 用户mid。
     * </pre>
     */
    @GetMapping(value = "/envelope/find.do")
    public void findEnvelope(@RequestParam(name = "userId") String userId) {
        try {

        } catch (Exception e) {

        } finally {

        }
    }

    /**
     * <pre>
     * consumEnvelopeAmount方法的作用：消费用户红包进度值。
     * @param userId 用户mid。
     * @param envelopeAmount 红包值。
     * </pre>
     */
    @GetMapping(value = "/envelope/consume.do")
    public void consumeEnvelope(@RequestParam(name = "userId") String userId,
                                @RequestParam(name = "envelopeAmount") BigDecimal envelopeAmount) {
        try {

        } catch (Exception e) {

        } finally {

        }
    }
}
