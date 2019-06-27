package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.converter.UserInfo2UserInfoDTOConverter;
import com.potato369.novel.app.web.converter.UserInfo2UserInfoVOConverter;
import com.potato369.novel.app.web.dto.UserInfoDTO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.app.web.vo.UserInfoVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.NovelVipGrade;
import com.potato369.novel.basic.dataobject.TaskRecordInfo;
import com.potato369.novel.basic.enums.TaskTypeEnum;
import com.potato369.novel.basic.enums.UserInfoGenderEnum;
import com.potato369.novel.basic.enums.UserInfoVIPGradeIdEnum;
import com.potato369.novel.basic.service.TaskRecordInfoService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.service.VipGradeService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName UserInfoController
 * @Desc UserInfoController
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 11:12
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private VipGradeService vipGradeService;

    @Autowired
    private TaskRecordInfoService taskRecordInfoService;

    /**
     * <pre>
     * 用户登录注册
     * @param userInfoDTO
     * @param bindingResult
     * @return ResultVO<UserInfoVO>
     * </pre>
     */
    @PostMapping(value = "/reg", produces = "application/json;charset=utf-8")
    public ResultVO<UserInfoVO> register(@RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        ResultVO<UserInfoVO> resultVO = new ResultVO<>();
        UserInfoVO userInfoVO = UserInfoVO.builder().build();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================【前端用户登录】====================start");
                log.debug("【前端用户登录】userInfoDTO用户信息={}", userInfoDTO);
            }
            if (bindingResult.hasErrors()) {
                resultVO.setMsg(bindingResult.getFieldError().getDefaultMessage());
                resultVO.setCode(-1);
                return resultVO;
            }
            String meId = null, openid = null, gradeName = null; // 手机串号；平台openid；等级名称
            Integer userType = null, gender = null; // 登录身份类型；性别
            NovelUserInfo novelUserInfo, userInfoResult;// 需要保存或者更新的对象信息；保存或者更新的对象结果信息
            Date nowDate = new Date();// 当前系统时间
            if (userInfoDTO != null) {
                meId = userInfoDTO.getMeId();
                openid = userInfoDTO.getOpenid();
                userType = userInfoDTO.getUserType();
                gender = userInfoDTO.getGender();
            }
            if (StringUtils.isNotEmpty(openid)) {//平台openid账号不覆盖登录
                novelUserInfo = userInfoService.findByMeIdAndOpenidAndUserType(meId, openid, userType);
                if (novelUserInfo == null) {
                    novelUserInfo = UserInfo2UserInfoDTOConverter.convert(userInfoDTO);
                    String mid = UUIDUtil.gen13MID();
                    novelUserInfo.setMId(mid);
                    if (UserInfoGenderEnum.WECHAT.getCode().equals(userType)) {
                        novelUserInfo.setIsOrNotBandWechat(UserInfoGenderEnum.FINISHED.getCode());
                        novelUserInfo.setBalanceAmount(new BigDecimal(6.66));
                        novelUserInfo.setBindWeChatOpenid(openid);
                        TaskRecordInfo recordInfo = TaskRecordInfo.builder().build();
                        recordInfo.setFinishedTime(new Date());
                        recordInfo.setTaskFinishedTimes(1);
                        recordInfo.setTaskId("0ed2ba762e364ce790661d86e59b162b");
                        recordInfo.setTaskRecordId(UUIDUtil.gen32UUID());
                        recordInfo.setTaskStatus(TaskTypeEnum.FINISHED.getCode());
                        recordInfo.setUserId(novelUserInfo.getMId());
                        taskRecordInfoService.save(recordInfo);
                    } else {
                        novelUserInfo.setIsOrNotBandWechat(UserInfoGenderEnum.UNFINISHED.getCode());
                        novelUserInfo.setBalanceAmount(BigDecimal.ZERO);
                    }
                    if (gender == null) {
                        novelUserInfo.setGender(UserInfoGenderEnum.GENDER_UNKNOWN.getCode());
                    }
                    NovelVipGrade vipGrade = vipGradeService.findOne(novelUserInfo.getVipGradeId());
                    if (vipGrade != null) {
                        gradeName = vipGrade.getGradeName();
                    }
                    novelUserInfo.setUserType(userType);
                    novelUserInfo.setVipStartTime(nowDate);
                    novelUserInfo.setVipEndTime(DateUtil.getAfterDayDate(nowDate, 7));
                    userInfoResult = userInfoService.save(novelUserInfo);
                    if (log.isDebugEnabled()) {
                        log.debug("【前端用户平台账号登录】保存用户信息，userInfo用户信息={}", novelUserInfo);
                    }
                } else {
                    novelUserInfo = vipOverDueOut(nowDate, novelUserInfo);
                    NovelVipGrade vipGrade = vipGradeService.findOne(novelUserInfo.getVipGradeId());
                    if (vipGrade != null) {
                        gradeName = vipGrade.getGradeName();
                    }
                    userInfoResult = userInfoService.update(novelUserInfo);
                    if (log.isDebugEnabled()) {
                        log.debug("【前端用户平台账号登录】更新用户信息，userInfo用户信息={}", novelUserInfo);
                    }
                }
            } else {
                novelUserInfo = userInfoService.findByUserMeIdAndUserType(meId, userType);
                if (novelUserInfo == null) {
                    novelUserInfo = UserInfo2UserInfoDTOConverter.convert(userInfoDTO);
                    if (UserInfoGenderEnum.WECHAT.getCode().equals(userType)) {
                        novelUserInfo.setIsOrNotBandWechat(UserInfoGenderEnum.FINISHED.getCode());
                        novelUserInfo.setBalanceAmount(new BigDecimal(6.66));
                        novelUserInfo.setBindWeChatOpenid(openid);
                    } else {
                        novelUserInfo.setIsOrNotBandWechat(UserInfoGenderEnum.UNFINISHED.getCode());
                        novelUserInfo.setBalanceAmount(BigDecimal.ZERO);
                    }
                    if (gender == null) {
                        novelUserInfo.setGender(UserInfoGenderEnum.GENDER_UNKNOWN.getCode());
                    }
                    NovelVipGrade vipGrade = vipGradeService.findOne(novelUserInfo.getVipGradeId());
                    if (vipGrade != null) {
                        gradeName = vipGrade.getGradeName();
                    }
                    novelUserInfo.setUserType(userType);
                    novelUserInfo.setMId(UUIDUtil.gen13MID());
                    novelUserInfo.setVipStartTime(nowDate);
                    novelUserInfo.setVipEndTime(DateUtil.getAfterDayDate(nowDate, 7));
                    userInfoResult = userInfoService.save(novelUserInfo);//平台openid账号不覆盖登录
                    if (log.isDebugEnabled()) {
                        log.debug("【前端用户平台账号登录】保存用户信息，userInfo用户信息={}", novelUserInfo);
                    }
                } else {
                    novelUserInfo = vipOverDueOut(nowDate, novelUserInfo);
                    NovelVipGrade vipGrade = vipGradeService.findOne(novelUserInfo.getVipGradeId());
                    if (vipGrade != null) {
                        gradeName = vipGrade.getGradeName();
                    }
                    userInfoResult = userInfoService.update(novelUserInfo);
                    if (log.isDebugEnabled()) {
                        log.debug("【前端用户平台账号登录】更新用户信息，userInfo用户信息={}", novelUserInfo);
                    }
                }
            }
            BeanUtils.copyProperties(userInfoResult, userInfoVO);
            userInfoVO.setVipGradeName(gradeName);
            resultVO.setCode(0);
            resultVO.setMsg("登录成功");
            resultVO.setData(userInfoVO);
            return resultVO;
        } catch (Exception e) {
            log.error("【前端用户登录】出现错误", e);
            resultVO.setMsg("登录失败");
            resultVO.setCode(-2);
            resultVO.setData(null);
            return resultVO;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================【前端用户登录】======================end ");
            }
        }
    }

    @GetMapping(value = "/find/{mid}")
    public ResultVO<UserInfoVO> find(@PathVariable(name = "mid") String mid) {
        ResultVO<UserInfoVO> result = new ResultVO<>();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start==================查找用户信息==================start");
            }
            NovelUserInfo userInfo = userInfoService.findById(mid);
            if (userInfo == null) {
                result.setCode(-1);
                result.setMsg("用户信息不存在");
                return result;
            }
            userInfo = vipOverDueOut(new Date(), userInfo);
            UserInfoVO userInfoVO = UserInfo2UserInfoVOConverter.convert(userInfo);
            NovelVipGrade vipGrade = vipGradeService.findOne(userInfo.getVipGradeId());
            String gradeName = UserInfoVIPGradeIdEnum.VIP0_NAME.getMessage();
            if (vipGrade != null) {
                gradeName = vipGrade.getGradeName();
            }
            userInfoVO.setVipGradeName(gradeName);
            result.setCode(0);
            result.setMsg("用户信息存在");
            result.setData(userInfoVO);
            return result;
        } catch (Exception e) {
            log.error("查找用户信息出现错误", e);
            result.setCode(-2);
            result.setMsg("查找用户信息出现错误");
            return result;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end====================查找用户信息====================end");
            }
        }
    }

    /**
     * 判断用户vip2是否过期，过期修改为vip0
     * @param now
     * @param userInfo
     * @return
     */
    private NovelUserInfo vipOverDueOut(Date now, NovelUserInfo userInfo) {
        if (userInfo != null) {
            Date vipEndTime = userInfo.getVipEndTime();
            if (DateUtil.compareDate(DateUtil.strFormat(now, DateUtil.sdfTimeFmt), DateUtil.strFormat(vipEndTime, DateUtil.sdfTimeFmt))) {
                userInfo.setVipGradeId(UserInfoVIPGradeIdEnum.VIP0.getMessage());
                userInfo = userInfoService.update(userInfo);
            }
        }
        return userInfo;
    }

    private NovelUserInfo updateUser(String gradeName, Date nowDate, NovelUserInfo novelUserInfo) {
        NovelUserInfo userInfoResult;
        novelUserInfo = vipOverDueOut(nowDate, novelUserInfo);
        NovelVipGrade vipGrade = vipGradeService.findOne(novelUserInfo.getVipGradeId());
        if (vipGrade != null) {
            gradeName = vipGrade.getGradeName();
        }
        userInfoResult = userInfoService.update(novelUserInfo);
        if (log.isDebugEnabled()) {
            log.debug("【前端用户平台账号登录】更新用户信息，userInfo用户信息={}", novelUserInfo);
        }
        return userInfoResult;
    }
}
