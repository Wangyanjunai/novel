package com.potato369.novel.app.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.potato369.novel.app.web.model.BizContentParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    @PostMapping(value = "/weChatPay/notify")
    public ModelAndView weChatPayNotify(@RequestBody String notifyData) {
        payService.weChatPayNotify(notifyData);//修改微信支付结果
        return new ModelAndView("pay/success");//返回给微信处理结果
    }

    /**
     * <pre>
     * 支付宝异步通知
     * @param request
     * @return
     * <pre>
     */
    @PostMapping(value = "/aliPay/notify.do")
    @ResponseBody
    public String aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("start==================支付宝异步返回支付结果==================start");
            }
            //1.从支付宝回调的request域中取值
            //获取支付宝返回的参数集合
            Map<String, String[]> aliParams = request.getParameterMap();
            //用以存放转化后的参数集合
            Map<String, String> conversionParams = new HashMap<>();
            for (Iterator<String> iter = aliParams.keySet().iterator(); iter.hasNext(); ) {
                String key = iter.next();
                String[] values = aliParams.get(key);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "uft-8");
                conversionParams.put(key, valueStr);
            }
            if (log.isDebugEnabled()) {
                log.debug("返回参数集合={}", conversionParams);
            }
            return payService.aliPayNotify(conversionParams);
        } catch (Exception e) {
            log.error("支付宝异步返回支付结果出现错误", e);
            return "fail";
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end====================支付宝异步返回支付结果====================end");
            }
        }
    }

    @GetMapping(value = "/aliPay/return.do")
    public ModelAndView aliPayReturn() {
        try {
            if (log.isDebugEnabled()) {
                log.debug("");
            }
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("");
            }
        }
        return new ModelAndView("pay/aliPayReturn");
    }

    /**
     * <pre>
     * 查询微信APP支付结果
     * @param outTradeNo
     * @return
     * </pre>
     */
    @GetMapping(value = "/queryWeChatPay.do")
    @ResponseBody
    public ResultVO<WeChatPayQueryResult> queryWechatPayResult(@RequestParam(name = "outTradeNo") String outTradeNo) {
        ResultVO<WeChatPayQueryResult> resultVO = new ResultVO<>();
        WeChatPayQueryResult result = new WeChatPayQueryResult();
        try {
            OrderMaster orderInfo = orderService.findOne(outTradeNo);
            if (orderInfo == null) {
                resultVO.setCode(0);
                resultVO.setMsg("返回数据成功。");
                result.setResultCode("PAY_SUCCESS");
                result.setReturnCode("PAY_SUCCESS");
                result.setErrCodeDes("订单信息不存在。");
                result.setErrCode("PAY_SUCCESS");
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
            result.setErrCode("PAY_FAIL");
            result.setResultCode("PAY_FAIL");
            result.setReturnCode("PAY_FAIL");
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
    @GetMapping(value = "/queryAliPay.do")
    @ResponseBody
    public ResultVO<AliPayQueryResult> queryAliPayResult(@RequestParam(name = "outTradeNo") String outTradeNo) {
        ResultVO<AliPayQueryResult> resultVO = new ResultVO<AliPayQueryResult>();
        AliPayQueryResult result = new AliPayQueryResult();
        try {
            OrderMaster orderInfo = orderService.findOne(outTradeNo);
            if (orderInfo == null) {
                resultVO.setCode(0);
                resultVO.setMsg("返回数据成功。");
                result.setResultCode("PAY_SUCCESS");
                result.setReturnCode("PAY_SUCCESS");
                result.setErrCodeDes("订单信息不存在。");
                result.setErrCode("PAY_SUCCESS");
                return resultVO;
            }
        } catch (Exception e) {
            resultVO.setCode(0);
            resultVO.setMsg("返回数据成功。");
            result.setResultCode("PAY_SUCCESS");
            result.setReturnCode("PAY_SUCCESS");
            result.setErrCodeDes("订单信息不存在。");
            result.setErrCode("PAY_SUCCESS");
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
                    StringUtils.trimToNull(this.properties.getAliPayPublicKey()),
                    StringUtils.trimToNull(this.properties.getSignType()));
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response != null && response.isSuccess()) {
                BeanUtils.copyProperties(response, result);
                switch (response.getTradeStatus()) {
                    case "TRADE_CLOSED":
                    case "TRADE_FINISHED":
                    case "WAIT_BUYER_PAY":
                        result.setErrCode("PAY_FAIL");
                        result.setErrCodeDes("返回数据失败。");
                        result.setResultCode("PAY_FAIL");
                        result.setReturnCode("PAY_FAIL");
                        result.setReturnMsg("返回数据失败。");
                        resultVO.setCode(-1);
                        resultVO.setMsg("返回数据失败。");
                        break;
                    case "TRADE_SUCCESS":
                        result.setErrCode("PAY_SUCCESS");
                        result.setErrCodeDes("返回数据成功。");
                        result.setResultCode("PAY_SUCCESS");
                        result.setReturnCode("PAY_SUCCESS");
                        result.setReturnMsg("返回数据成功。");
                        resultVO.setCode(0);
                        resultVO.setMsg("返回数据成功。");
                        break;
                    default:
                        break;
                }
                resultVO.setData(result);
            }
            return resultVO;
        } catch (Exception e) {
            log.error("返回数据失败", e);
            result.setErrCode("PAY_FAIL");
            result.setErrCodeDes("返回数据失败。");
            result.setResultCode("PAY_FAIL");
            result.setReturnCode("PAY_FAIL");
            result.setReturnMsg("返回数据失败。");
            resultVO.setCode(-1);
            resultVO.setMsg("返回数据失败。");
            resultVO.setData(result);
            return resultVO;
        }
    }
}

