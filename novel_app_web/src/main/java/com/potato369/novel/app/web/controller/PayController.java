package com.potato369.novel.app.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.potato369.novel.app.web.service.PayService;
/**
 * <pre>
 * 类名: PayController
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年6月17日 下午2:57:09
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Controller
@RequestMapping(value = "/pay")
public class PayController {
	
	@Autowired
	private PayService payService;
	
	 /**
	  * <pre>
	  * 微信异步通知
	  * @param notifyData
	  * @return ModelAndView
	  * <pre>
	  */
	  @PostMapping(value = "/notify")
	  public ModelAndView notify(@RequestBody String notifyData) {
	    payService.notify(notifyData);//修改微信支付结果
	    return new ModelAndView("pay/success");//返回给微信处理结果
	  }
	  
  	/**
	  * <pre>
	  * 支付宝异步通知
	  * @param request
	  * @return
	  * <pre>
	  */
	  @PostMapping(value = "/notify1")
	  public void notify1(HttpServletRequest request) {
		payService.notify1(request);
	  }
}
