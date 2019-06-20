package com.potato369.novel.app.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.potato369.novel.app.web.conf.prop.AliPayProperties;
import com.potato369.novel.app.web.model.AliPayQueryResult;
import com.potato369.novel.app.web.model.WeChatPayQueryResult;
import com.potato369.novel.app.web.service.PayService;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.basic.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PayController {
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AliPayProperties properties;
	
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
	  
	  /**
	   * <pre>
	   * 查询微信APP支付结果
	   * @param outTradeNo
	   * @return
	   * </pre>
	   */
	  @GetMapping(value = "/query")
	  @ResponseBody
	  public ResultVO<WeChatPayQueryResult> query(@RequestParam(name = "outTradeNo") String outTradeNo) {
		  ResultVO<WeChatPayQueryResult> resultVO = new ResultVO<>();
		  WeChatPayQueryResult result = new WeChatPayQueryResult();
		  try {
			OrderMaster orderInfo = orderService.findOne(outTradeNo);
			if (orderInfo == null) {
				resultVO.setCode(0);
				resultVO.setMsg("返回数据成功。");
				result.setResultCode("SUCCESS");
				result.setReturnCode("SUCCESS");
				result.setErrCodeDes("订单信息不存在。");
				result.setErrCode("SUCCESS");
				resultVO.setData(result);
				return resultVO;
			}
			result = payService.queryWeChatPayResult(outTradeNo);
		    resultVO.setCode(0);
		    resultVO.setMsg("返回数据成功。");
		    resultVO.setData(result);
		    return resultVO;
		} catch (Exception e) {
			log.error("查询订单出现错误。", e);
			resultVO.setCode(0);
			resultVO.setMsg("返回数据失败。");
			result.setErrCode("FAIL");
			result.setResultCode("FAIL");
			result.setReturnCode("FAIL");
			result.setErrCodeDes("查询订单出现错误。");
			resultVO.setData(result);
			return resultVO;
		}
	  }
	  
	  /**
	   * <pre>
	   * 查询支付宝APP支付结果
	   * @param outTradeNo
	   * @return
	   * </pre>
	   */
	  @GetMapping(value = "/query1")
	  @ResponseBody
	  public ResultVO<AliPayQueryResult> query1(@RequestParam(name = "outTradeNo") String outTradeNo) {
		ResultVO<AliPayQueryResult> resultVO = new ResultVO<AliPayQueryResult>();
		AliPayQueryResult result = new AliPayQueryResult();
		try {
			OrderMaster orderInfo = orderService.findOne(outTradeNo);
			if (orderInfo == null) {
				resultVO.setCode(0);
				resultVO.setMsg("返回数据成功。");
				result.setResultCode("SUCCESS");
				result.setReturnCode("SUCCESS");
				result.setErrCodeDes("订单信息不存在。");
				result.setErrCode("SUCCESS");
				return resultVO;
			}
		} catch (Exception e) {
			resultVO.setCode(0);
			resultVO.setMsg("返回数据成功。");
			result.setResultCode("SUCCESS");
			result.setReturnCode("SUCCESS");
			result.setErrCodeDes("订单信息不存在。");
			result.setErrCode("SUCCESS");
			return resultVO;
		}
		try {
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			BizContentParams bizContentParams = BizContentParams.builder().build();
			bizContentParams.setOutTradeNo(outTradeNo);
			String bizContent = JSONObject.toJSONString(bizContentParams);
			request.setBizContent(bizContent);
			AlipayClient alipayClient = new DefaultAlipayClient(
					StringUtils.trimToNull(this.properties.getServerPayUrl()),
					StringUtils.trimToNull(this.properties.getAppId()),
					StringUtils.trimToNull(this.properties.getAppPrivateKey()),
					StringUtils.trimToNull(this.properties.getFormat()),
					StringUtils.trimToNull(this.properties.getCharSet()),
					StringUtils.trimToNull(this.properties.getAlipayPublicKey()),
					StringUtils.trimToNull(this.properties.getSignType()));
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			if (response != null) {
				String code = response.getCode();
				if ("10000".equals(code)) {
					String msg = response.getMsg();
					result.setErrCode("SUCCESS");
					result.setErrCodeDes("返回数据成功。");
					result.setOpenid(response.getBuyerUserId());
					result.setResultCode("SUCCESS");
					result.setReturnCode("SUCCESS");
					result.setReturnMsg(msg);
					resultVO.setCode(0);
					resultVO.setMsg("返回数据成功");
					resultVO.setData(result);
					return resultVO;
				}
				result.setErrCode("FAIL");
				result.setErrCodeDes("返回数据失败。");
				result.setResultCode("FAIL");
				result.setReturnCode("FAIL");
				result.setReturnMsg("返回数据失败。");
				resultVO.setCode(-1);
				resultVO.setMsg("返回数据失败。");
				return resultVO;
			}
			result.setErrCode("FAIL");
			result.setErrCodeDes("返回数据失败。");
			result.setResultCode("FAIL");
			result.setReturnCode("FAIL");
			result.setReturnMsg("返回数据失败。");
			resultVO.setCode(-1);
			resultVO.setMsg("返回数据失败。");
			return resultVO;
		} catch (Exception e) {
			log.error("返回数据失败", e);
			result.setErrCode("FAIL");
			result.setErrCodeDes("返回数据失败。");
			result.setResultCode("FAIL");
			result.setReturnCode("FAIL");
			result.setReturnMsg("返回数据失败。");
			resultVO.setCode(-1);
			resultVO.setMsg("返回数据失败。");
			resultVO.setData(result);
			return resultVO;
		}
	  }
}

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
class BizContentParams {
	
	/**
	 * <pre>
	 * @JsonProperty outTradeNo：商户订单号；说明：订单支付时传入的商户订单号，和支付宝交易号不能同时为空。 trade_no，out_trade_no如果同时存在优先取trade_no。
	 * </pre>
	 */
	@JSONField(name = "out_trade_no")
	private String outTradeNo;
	
	/**
	 * <pre>
	 * @JsonProperty tradeNo：支付宝交易号；说明：支付宝交易号，和商户订单号不能同时为空。
	 * </pre>
	 */
	@JSONField(name = "trade_no")
	private String tradeNo;
	
	/**
	 * <pre>
	 * @JsonProperty orgPid：银行间联模式下有用，其它场景请不要使用； 双联通过该参数指定需要查询的交易所属收单机构的pid。
	 * </pre>
	 */
	@JSONField(name = "org_pid")
	private String orgPid;
	
	/**
	 * <pre>
	 * @JsonProperty queryOptions：查询选项参数。
	 * </pre>
	 */
	@JSONField(name = "query_options")
	private String queryOptions;
}