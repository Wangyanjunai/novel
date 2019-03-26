package com.potato369.novel.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName SellerWeChatController
 * @Desc 卖家微信相关后台管理Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/25 14:35
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Controller
@RequestMapping(value = "/weChat")
@Slf4j
public class SellerWeChatController {
	
	@Autowired
	public WxMpService wxMpService;
	
	@GetMapping(value = "/list")
	public ModelAndView list() {
		return null;
	}
}
