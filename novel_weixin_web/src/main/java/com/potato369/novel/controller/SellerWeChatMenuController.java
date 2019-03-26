package com.potato369.novel.controller;

import com.potato369.novel.basic.dataobject.MenuButtonInfo;
import com.potato369.novel.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * @PackageName com.potato369.novel.controller
 * @ClassName SellerWeChatMenuController
 * @Desc 微信公众号自定义菜单Controller
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/25 14:45
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Controller
@RequestMapping(value = "/menu")
@Slf4j
public class SellerWeChatMenuController {

	@Autowired
	private WxMpService wxMpService;

	@GetMapping(value = "/list")
	public ModelAndView list(Map<String, Object> map) {
		if (log.isDebugEnabled()) {
			log.debug("【卖家端后台管理】开始从微信服务器端获取微信公众号自定义菜单列表");
		}
		try {
			WxMpMenu menu = wxMpService.getMenuService().menuGet();
			WxMpMenu.WxMpConditionalMenu mpConditionalMenu = menu.getMenu();
			String menuId = mpConditionalMenu.getMenuId();
			List<WxMenuButton> menuButtonList = mpConditionalMenu.getButtons();
			List<MenuButtonInfo> menuButtonInfoList = new ArrayList<>();
			for (WxMenuButton menuButton : menuButtonList) {
				MenuButtonInfo menuButtonInfo = MenuButtonInfo.builder().build();
				BeanUtils.copyProperties(menuButton, menuButtonInfo);
				menuButtonInfo.setMenuId(menuId);
				menuButtonInfoList.add(menuButtonInfo);
			}
			if (log.isDebugEnabled()) {
				log.debug("【卖家端后台管理】从微信服务器端获取微信公众号自定义菜单列表，返回结果mpConditionalMenu={}", JsonUtil.toJson(mpConditionalMenu));
			}
			if (log.isDebugEnabled()) {
				log.debug("【卖家端后台管理】从微信服务器端获取微信公众号自定义菜单列表，返回结果menuButtonInfoList={}", JsonUtil.toJson(menuButtonInfoList));
			}
			if (log.isDebugEnabled()) {
				log.debug("【卖家端后台管理】结束从微信服务器端获取微信公众号自定义菜单列表");
			}
		} catch (Exception e) {
			log.error("【卖家端后台管理】从微信服务器端获取微信公众号自定义菜单列表，出现错误，e={}", e);
		}
		return new ModelAndView("menu/list", map);
	}
	
	@GetMapping(value = "/index")
	public ModelAndView index() {
		return null;
	}
	
	@PostMapping(value = "/save")
	public ModelAndView save() {
		return null;
	}
}
