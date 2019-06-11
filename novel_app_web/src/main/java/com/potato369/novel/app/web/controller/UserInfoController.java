package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.converter.UserInfo2UserInfoDTOConverter;
import com.potato369.novel.app.web.dto.UserInfoDTO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.app.web.vo.UserInfoVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.NovelVipGrade;
import com.potato369.novel.basic.enums.UserInfoEnum;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.service.VipGradeService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.Valid;

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
    
    /**
     * <pre>
     * 登录注册
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
                resultVO.setData(null);
                return resultVO;
            }
            String meId = null; // 用户手机串号
            String openid = null; // 用户平台openid
            Integer userType = null;// 用户登录类型
            NovelUserInfo novelUserInfo = null;// 需要保存或者更新的对象信息
            NovelUserInfo userInfoResult = null;// 保存或者更新的对象结果信息
            Date nowDate = new Date();// 当前系统时间
            String gradeName = null;
            Integer gender = null;
            if (userInfoDTO != null) {
                meId = userInfoDTO.getMeId();
                openid = userInfoDTO.getOpenid();
                userType = userInfoDTO.getUserType();
                gender = userInfoDTO.getGender();
            }
            if (StringUtils.isNotEmpty(openid)) {// 用户平台openid不为空
            	novelUserInfo = userInfoService.findByOpenid(openid);// 首先根据用户平台openid去查找用户信息
            	if (novelUserInfo == null) {
            		novelUserInfo = UserInfo2UserInfoDTOConverter.convert(userInfoDTO);
            		if (UserInfoEnum.WECHAT.getCode().equals(userType)) {
            			novelUserInfo.setIsOrNotBandWechat(UserInfoEnum.FINISHED.getCode());
            			novelUserInfo.setBalanceAmount(new BigDecimal(6.66));
					} else {
						novelUserInfo.setIsOrNotBandWechat(UserInfoEnum.UNFINISHED.getCode());
						novelUserInfo.setBalanceAmount(BigDecimal.ZERO);
					}
            		if (gender == null) {
            			novelUserInfo.setGender(UserInfoEnum.GENDER_UNKNOWN.getCode());
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
					BeanUtils.copyProperties(userInfoDTO, novelUserInfo);
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
	            if (StringUtils.isNotEmpty(meId)) { // 手机串号不为空
	            	novelUserInfo = userInfoService.findByUserMeId(meId);
	            	if (novelUserInfo == null) {
	                	novelUserInfo = UserInfo2UserInfoDTOConverter.convert(userInfoDTO);
	                	if (UserInfoEnum.WECHAT.getCode().equals(userType)) {
	            			novelUserInfo.setIsOrNotBandWechat(UserInfoEnum.FINISHED.getCode());
	            			novelUserInfo.setBalanceAmount(new BigDecimal(6.66));
						} else {
							novelUserInfo.setIsOrNotBandWechat(UserInfoEnum.UNFINISHED.getCode());
							novelUserInfo.setBalanceAmount(BigDecimal.ZERO);
						}
	                	NovelVipGrade vipGrade = vipGradeService.findOne(novelUserInfo.getVipGradeId());
	            		if (vipGrade != null) {
							gradeName = vipGrade.getGradeName();
						}
	            		if (gender == null) {
	            			novelUserInfo.setGender(UserInfoEnum.GENDER_UNKNOWN.getCode());
						}
	                	novelUserInfo.setUserType(userType);
	            		novelUserInfo.setMId(UUIDUtil.gen13MID());
	            		novelUserInfo.setVipStartTime(nowDate);
	            		novelUserInfo.setVipEndTime(DateUtil.getAfterDayDate(nowDate, 7));
	                    userInfoResult = userInfoService.save(novelUserInfo);// 保存用户信息
	                    if (log.isDebugEnabled()) {
	                        log.debug("【前端用户游客身份登录】保存用户信息，userInfo用户信息={}", novelUserInfo);
	                    }
					} else {
						BeanUtils.copyProperties(userInfoDTO, novelUserInfo);
						NovelVipGrade vipGrade = vipGradeService.findOne(novelUserInfo.getVipGradeId());
	            		if (vipGrade != null) {
							gradeName = vipGrade.getGradeName();
						}
						userInfoResult = userInfoService.update(novelUserInfo);// 更新用户信息
            			if (log.isDebugEnabled()) {
                            log.debug("【前端用户游客身份登录】更新用户信息，userInfo用户信息={}", userInfoResult);
                        }
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
}
