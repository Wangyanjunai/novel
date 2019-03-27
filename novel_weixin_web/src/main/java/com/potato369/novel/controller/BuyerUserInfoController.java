package com.potato369.novel.controller;

import com.potato369.novel.basic.dataobject.UserInfo;
import com.potato369.novel.basic.enums.ResultEnum;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.utils.ResultVOUtil;
import com.potato369.novel.vo.ResultVO;
import com.potato369.novel.vo.UserInfoVO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName UserInfoController
 * @Desc 用户Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/03 15:45
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class BuyerUserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @SuppressWarnings("unchecked")
	@GetMapping(value = "/info")
    public ResultVO<UserInfoVO> info(@RequestParam(name = "openid", required = true) String openid) {
        if (StringUtils.isEmpty(openid)) {
            return ResultVOUtil.error(ResultEnum.MP_OPENID_ERROR.getCode(), ResultEnum.MP_OPENID_ERROR.getMessage());
        }
        ResultVO<UserInfoVO> resultVO = new ResultVO<>();
        try {
			if (log.isDebugEnabled()) {
				log.debug("【买家前端数据】开始根据用户微信openid={}查找用户信息", openid);
			}
			UserInfo userInfo = userInfoService.findByOpenid(openid);
	        UserInfoVO userInfoVO = new UserInfoVO();
	        userInfoVO.setId(userInfo.getPrivatedId());
	        userInfoVO.setBalance(userInfo.getBalance());
	        userInfoVO.setUserAvatar(userInfo.getAvatarUrl());
	        userInfoVO.setUserNickName(userInfo.getNickName());
	        resultVO.setTotal(BigDecimal.ONE);
	        resultVO.setData(userInfoVO);
	        resultVO.setMsg("根据用户微信openid查询用户信息数据成功");
	        resultVO.setErr_no(0);
	        return resultVO;
		} catch (Exception e) {
			log.error("【买家前端数据】根据用户微信openid={}查找用户信息出现错误", e);
            return ResultVOUtil.error(ResultEnum.MP_OPENID_ERROR.getCode(), e.getMessage());
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("【买家前端数据】结束根据用户微信openid={}查找用户信息", openid);
			}
		}
    }
}
