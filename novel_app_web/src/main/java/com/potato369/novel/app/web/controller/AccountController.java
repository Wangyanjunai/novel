package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.converter.NovelUserAccount2AccountDTOConverter;
import com.potato369.novel.app.web.dto.AccountDTO;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.NovelUserAccount;
import com.potato369.novel.basic.service.UserAccountService;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
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
 * @ClassName AccountController
 * @Desc 用户提现账号记录信息controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/12 14:32
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@RestController
@RequestMapping("/account")
@Slf4j
@SuppressWarnings("unchecked")
public class AccountController {

    @Autowired
    private UserAccountService userAccountService;

    /**
     * {
     * 	"name":"支付宝",
     * 	"info":"lihao@potato369.com",
     * 	"userId":"1560233672037",
     * 	"userName":"李浩",
     * 	"idNumber":"430421199104135236",
     * 	"phoneNumber":"13852369856"
     * }
     * 绑定提现账户信息接口
     * @param accountDTO
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/binding", produces = "application/json;charset=utf-8")
    public ResultVO binding(@RequestBody @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        ResultVO resultVO = new ResultVO();
        try {
            if (log.isDebugEnabled()) {
                log.debug("start====================绑定提现账户====================start");
            }
            if (bindingResult.hasErrors()) {
                resultVO.setMsg(bindingResult.getFieldError().getDefaultMessage());
                resultVO.setCode(-2);
                return resultVO;
            }
            String name = null;
            String mid = null;
            NovelUserAccount userAccount;
            if (accountDTO != null) {
                name = accountDTO.getAccountName();
                mid = accountDTO.getUserId();
            }
            userAccount = userAccountService.findByUserIdAndAccountName(mid, name);
            if (userAccount == null) {
                userAccount = NovelUserAccount2AccountDTOConverter.convert(accountDTO);
                userAccount.setAccountId(UUIDUtil.gen32UUID());
                userAccountService.save(userAccount);
                resultVO.setMsg("绑定”"+name+"“提现账户成功");
                resultVO.setCode(0);
                return resultVO;
            } else {
                userAccountService.update(userAccount);
                resultVO.setMsg("已经绑定”"+name+"“提现账户，不需要重新绑定");
                resultVO.setCode(0);
                return resultVO;
            }
        } catch (Exception e) {
            log.error("绑定提现账户失败", e);
            resultVO.setMsg("绑定提现账户失败");
            resultVO.setCode(-1);
            return resultVO;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================绑定提现账户======================end");
            }
        }
    }
}
