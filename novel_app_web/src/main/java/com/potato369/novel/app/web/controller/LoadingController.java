package com.potato369.novel.app.web.controller;

import com.potato369.novel.basic.dataobject.NovelAdvertisement;
import com.potato369.novel.basic.service.AdvertisementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.LoadingDataVO;
import com.potato369.novel.app.web.vo.ResultVO;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName LoadingController
 * @Desc 急速追书小说app初始化加载数据接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/16 15:48
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value = "/loading")
public class LoadingController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * @api {GET} /loading/getData 加载广告图片
     * @apiVersion 0.0.1
     * @apiGroup Init
     * @apiDescription 急速追书小说APP初始页面加载广告图片接口
     * @apiSuccess (200) {String} id 广告id 必须
     * @apiSuccess (200) {int} tag 标识是否广告可以跳转  0-可以跳转，1-不可以跳转 必须
     * @apiSuccess (200) {String} imageUrl 图片链接 必须
     * @apiSuccess (200) {String} linkUrl 图片跳转链接 不是必须
     * @apiSuccess (200) {int} code 消息代码   0-代表无错误， 1-代表有错误 必须
     * @apiSuccess (200) {String} msg 信息
     * @apiSuccessExample {json} 正确返回样例:
     *                    {
     *                    	"code":"0", 
     *                    	"msg":"返回数据成功", 
     *                    	"data":{
     *                    		"id":"123456",
     *                    		"tag":"0",
     *                    		"imageUrl":"https://encrypted-tbn2.gstatic.cn/shopping?q=tbn:ANd9GcQj-B2D8AM-Xyj-d2S8FwG2D5uv1q5-lKvsBEb0fA2ehzNeApn0zio&usqp=CAI",
     *                    		"linkUrl":"https://www.googleadservices.com/pagead/aclk?sa=L&ai=CiTcEf9y3XNGxJsXgqAG7pZ9gnPXLm1amnrqQ_gWOmsjQlQIQASCfgc8mYJ0BoAHu7ND8A8gBCakCsGue910RhD6oAwHIA0qqBLgBT9DzkII6pz7jzyXsO4LcTNaBIV0xy-TamnaGNI8OeZZdoFha4AUp4QxRe-8dsFrYhOVNNREps-TPkJcKLpwAVnOAuBjbT6rCTRiZjaFt-bC9qSYzlln5RD_Qqyr-6M6cr-AHX-j9glgMX_d6Qa2wxTQjarrx4x_zULHDR0EyfETh6LG2xHwyH_jL5QXVpgLaUa0skeK0kVMp8tQZctlAZFIfJve6nu78g6PyWYCqGvUX3nKPkx-1a_oFBgglEAEYAKAGLoAH-pKvA6gHjs4bqAfg0xuoB9nLG6gHz8wbqAemvhvYBwDACAHSCAcIgGEQARgGgAoB2BMM&ae=1&num=1&cid=CAASEuRoArAhN__fu9CKZoCo0CkD5g&sig=AOD64_2aLRsq6uKaYpEQf5EfsTMuj7jXgw&client=ca-pub-1901018673541035&adurl=https://union-click.jd.com/sem.php%3Fsource%3Dgoogle-union%26unionId%3D262767352%26siteId%3Dgoogleunion_217522390047%26to%3Dhttps://re.jd.com/item/2853250.html%253Fre_dcp%3D22um2D2ZOw%26gclid%3DEAIaIQobChMIke2pgsjY4QIVRTAqCh270gcMEAEYASABEgJumPD_BwE"
     *                    	}
     *                    }
     * @apiError {json} 错误返回样例:
     *					  {
     *                    	"code":"1", 
     *                    	"msg":"返回数据失败，服务器故障", 
     *                    	"data":null
     *                    }                 
     */
    @GetMapping(value = "/getData")
    public ResultVO<LoadingDataVO> getData() {
        LoadingDataVO loadingDataVO = LoadingDataVO.builder().build();
        try {
            if (log.isDebugEnabled()) {
                log.debug("【急速追书后台APP接口】开始查找首页初始加载的广告信息");
            }
            Sort sort = new Sort(Sort.Direction.DESC, "createTime", "updateTime");
            List<NovelAdvertisement> advertisementList = advertisementService.findAll(sort);
            if (advertisementList != null && !advertisementList.isEmpty() && advertisementList.size() > 0) {
                NovelAdvertisement advertisement = advertisementList.get(0);
                if (advertisement != null) {
                    BeanUtils.copyProperties(advertisement, loadingDataVO);
                    loadingDataVO.setId(advertisement.getAdId());
                }
            }
        } catch (Exception e) {
            log.error("【急速追书后台APP接口】查找首页初始加载的广告信息出现错误", e);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("【急速追书后台APP接口】结束查找首页初始加载的广告信息");
            }
        }
    	return ResultVOUtil.success(loadingDataVO);
    }
}
