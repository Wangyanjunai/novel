package com.potato369.novel.app.web.controller;

import com.potato369.novel.app.web.converter.ProductInfo2ProductInfoVOConverter;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.ProductInfoVO;
import com.potato369.novel.app.web.vo.ProductVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.ProductInfo;
import com.potato369.novel.basic.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * @ClassName: BuyerProductController
 * @Function:
 * @Reason: 买家商品Controller
 * @Date: 2019年6月10日 下午4:08:45
 * @Desc:提供前端充值或者兑换的商品信息列表
 * @author 王艳军
 * @version 1.0
 * @since JDK 1.6
 * <pre>
 */
@RestController
@RequestMapping("/product")
@Slf4j
@SuppressWarnings("unchecked")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/list/{type}")
    public ResultVO<ProductVO> list(@PathVariable(name = "type") Integer type) {
        try {
            ResultVO<ProductVO> resultVO = new ResultVO<>();
            if (log.isDebugEnabled()) {
                log.debug("start====================后端获取产品信息数据====================start");
            }
            ProductVO productVO = ProductVO.builder().build();
            List<ProductInfo> productInfoList = productService.findAllByType(type);
            List<ProductInfoVO> productInfoVOList = ProductInfo2ProductInfoVOConverter.convertToProductInfoVOList(productInfoList);
            productVO.setList(productInfoVOList);
            productVO.setTotalPage(BigDecimal.ONE);
            resultVO.setCode(0);
            resultVO.setData(productVO);
            resultVO.setMsg("返回数据成功");
            return resultVO;
        } catch (Exception e) {
            log.error("后端获取产品信息数据出现错误", e);
            return ResultVOUtil.error(-1, "返回数据失败");
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("end======================后端获取产品信息数据======================end");
            }
        }
    }
}
