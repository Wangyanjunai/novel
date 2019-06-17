package com.potato369.novel.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	  * 微信异步通知
	  * @param notifyData
	  * @return
	  */
	  @PostMapping(value = "/notify")
	  public String notify(@RequestBody String notifyData) {
	    payService.notify(notifyData);
	    //3、返回给微信处理结果
	    return "<xml>\r\n" + 
	    			"<return_code><![CDATA[SUCCESS]]></return_code>\r\n" + 
	    			"<return_msg><![CDATA[OK]]></return_msg>\r\n" + 
	    		"</xml>";
	  }
}
