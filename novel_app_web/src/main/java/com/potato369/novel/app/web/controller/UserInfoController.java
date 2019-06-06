package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.converter.UserInfo2UserInfoDTOConverter;
import com.potato369.novel.app.web.dto.UserInfoDTO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.app.web.vo.UserInfoVO;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.service.UserInfoService;
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

    /**
     * {
     * 	"meId": "qqqqq",
     * 	"brand": "张三",
     * 	"model": "11111111",
     * 	"mac": "121222222222",
     * 	"systemName": "121222222222",
     * 	"systemCode": "121222222222",
     * 	"versionName": "121222222222",
     * 	"openid": "121222222222",
     * 	"nickName": "121222222222",
     * 	"gender": 0,
     * 	"avatarUrl": "121222222222",
     * 	"ip": "183.14.29.207"
     * }
     * @param userInfoDTO
     * @param bindingResult
     * @return
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
            //String mId = null;//用户mId
            String meId = null;//用户手机串号
            String openid = null;//用户平台openid
            if (userInfoDTO != null) {
//                mId = userInfoDTO.getMId();
                meId = userInfoDTO.getMeId();
                openid = userInfoDTO.getOpenid();
            }
            NovelUserInfo novelUserInfo = null;
            NovelUserInfo userInfoResult = null;
            if (StringUtils.isNotEmpty(meId)) {//手机串号不为空
            	novelUserInfo = userInfoService.findByUserMeId(meId);
            	if (novelUserInfo == null) {
                	novelUserInfo = UserInfo2UserInfoDTOConverter.convert(userInfoDTO);
            		novelUserInfo.setMId(UUIDUtil.gen13MID());
                    userInfoResult = userInfoService.save(novelUserInfo);
                    if (log.isDebugEnabled()) {
                        log.debug("【前端用户登录】保存用户信息，userInfo用户信息={}", novelUserInfo);
                    }
				} else {
					novelUserInfo = UserInfo2UserInfoDTOConverter.convert(userInfoDTO);
					userInfoResult = userInfoService.update(novelUserInfo);
					if (log.isDebugEnabled()) {
                        log.debug("【前端用户登录】更新用户信息，userInfo用户信息={}", novelUserInfo);
                    }
					if (StringUtils.isNotEmpty(openid)) {//用户平台openid不为空
	            		novelUserInfo = userInfoService.findByMeIdAndOpenid(meId, openid);
	            		if (novelUserInfo != null) {//根据用户手机串号和平台openid查询的用户信息为空，创建一个新用户
	            			userInfoResult = userInfoService.update(novelUserInfo);// 更新用户信息
	            			if (log.isDebugEnabled()) {
	                            log.debug("【前端用户登录】更新用户信息，userInfo用户信息={}", userInfoResult);
	                        }
	    				} else {
	    					novelUserInfo = UserInfo2UserInfoDTOConverter.convert(userInfoDTO);
	                		novelUserInfo.setMId(UUIDUtil.gen13MID());
	                        userInfoResult = userInfoService.save(novelUserInfo);
	                        if (log.isDebugEnabled()) {
	                            log.debug("【前端用户登录】保存用户信息，userInfo用户信息={}", novelUserInfo);
	                        }
						}
					}
				}
            }
            BeanUtils.copyProperties(userInfoResult, userInfoVO);
            resultVO.setCode(0);
            resultVO.setMsg("登录成功");
            resultVO.setData(userInfoVO);
            return resultVO;
        } catch (Exception e) {
            log.error("", e);
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
