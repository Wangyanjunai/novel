package com.potato369.novel.app.web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.potato369.novel.app.web.converter.NovelInfo2NovelInfoVOConverter;
import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.NovelInfoVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.app.web.vo.ShelfDetailInfoVO;
import com.potato369.novel.app.web.vo.ShelfInfoVO;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.dataobject.NovelShelf;
import com.potato369.novel.basic.dataobject.NovelShelfDetail;
import com.potato369.novel.basic.dataobject.NovelUserInfo;
import com.potato369.novel.basic.dataobject.idClass.NovelShelfDetailIdClass;
import com.potato369.novel.basic.dataobject.idClass.NovelUserInfoIdClass;
import com.potato369.novel.basic.service.NovelInfoService;
import com.potato369.novel.basic.service.ShelfDetailService;
import com.potato369.novel.basic.service.ShelfService;
import com.potato369.novel.basic.service.UserInfoService;
import com.potato369.novel.basic.utils.DateUtil;
import com.potato369.novel.basic.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName ShelfController
 * @Desc 书架控制器类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 14:56
 * @CreateBy InTellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value = "/shelf")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    @Autowired
    private ShelfDetailService detailService;

    @Autowired
    private NovelInfoService novelInfoService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * <pre>
     * 获取用户书架信息
     * @param userId 用户id
     * </pre>
     */
    @GetMapping(value = "/get")
    public ResultVO<ShelfInfoVO> getShelf(@RequestParam(name = "userId") String userId) {
        ResultVO<ShelfInfoVO> shelfInfoVOResultVO = new ResultVO<ShelfInfoVO>();
        ShelfInfoVO shelfInfoVO = ShelfInfoVO.builder().build();
        try {
        	if (log.isDebugEnabled()) {
				log.debug("start====================获取用户书架信息====================start");
			}
            NovelShelf novelShelf = shelfService.selectByUserId(userId);
            String shelfId = null;
            if (novelShelf != null) {
                shelfId = novelShelf.getShelfId();
            }
            BeanUtils.copyProperties(novelShelf, shelfInfoVO);
            List<NovelShelfDetail> shelfDetailList = detailService.selectByUserIdAndShelfId(userId, shelfId);
            List<ShelfDetailInfoVO> shelfDetailInfoVOList = new ArrayList<>();
            if (shelfDetailList != null && !shelfDetailList.isEmpty() && shelfDetailList.size() > 0) {
                for (NovelShelfDetail shelfDetail : shelfDetailList) {
                    ShelfDetailInfoVO shelfDetailInfoVO = ShelfDetailInfoVO.builder().build();
                    BeanUtils.copyProperties(shelfDetail, shelfDetailInfoVO);
                    String novelId = shelfDetail.getNovelId();
                    if (StringUtils.isNotEmpty(novelId)) {
                        NovelInfo novelInfo = novelInfoService.findById(novelId);
                        if (novelInfo != null) {
                            NovelInfoVO novelInfoVO = NovelInfo2NovelInfoVOConverter.convert(novelInfo);
                            shelfDetailInfoVO.setNovel(novelInfoVO);
                        }
                    }
                    shelfDetailInfoVOList.add(shelfDetailInfoVO);
                }
            }
            shelfInfoVO.setShelfDetailInfoVOList(shelfDetailInfoVOList);
            shelfInfoVOResultVO.setData(shelfInfoVO);
            shelfInfoVOResultVO.setCode(0);
            shelfInfoVOResultVO.setMsg("返回数据成功");
        } catch (Exception e) {
            log.error("获取用户书架信息出现错误", e);
        } finally {
        	if (log.isDebugEnabled()) {
				log.debug("end======================获取用户书架信息======================end");
			}
        }
        return shelfInfoVOResultVO;
    }

    /**
     * <pre>
     * 将小说添加到书架
     * 用户id，小说id，书架id，小说章节id，章节索引
     * json:{
     *          "userId": "036a16dd9a8645f48dff1fd96af07877",
     *          "novelId": "00ace806021b4c0f89df574467755a53",
     *          "lastReadChapterId": "25b9f4c828e648b89a42376407fb67fe",
     *          "lastReadChapterIndex": 17,
     *          "lastReadPage": 1,
     *          "hasUpdate": 0,
     *          "isOrNotTop": 0,
     *          "isOrNotPush": 0,
     *          "sort": 2,
     *          "lastChapterUpdateTime": "2019-05-16 12:22:58",
     *          "lastReadTime": "2019-05-17 11:22:58"
     *      }
     * </pre>
     */
	@PostMapping(value = "/add")
    public ResultVO addToShelf(@RequestParam(name = "json") String shelfJson) {
        try {
            if (log.isDebugEnabled()) {
                log.info("shelf json={}", shelfJson);
            }
            Map<String, Object> shelfMap = (LinkedHashMap<String, Object>) JSONUtils.parse(shelfJson);
            if (log.isDebugEnabled()) {
                log.info("shelf map={}", shelfMap);
            }
            if (log.isDebugEnabled()) {
                log.info("start====================将小说添加到用户书架====================start");
            }
            if (shelfMap != null && !shelfMap.isEmpty() && shelfMap.size() > 0) {
                //根据用户id查询用户书架和用户信息
                NovelUserInfoIdClass idClass = NovelUserInfoIdClass.builder().build();
                if (shelfMap.containsKey("userId")) {//用户id
                    String userId = (String) shelfMap.get("userId");
                    idClass.setMId(userId);
                    NovelUserInfo novelUserInfo = userInfoService.findByUserId(userId);
                    if (novelUserInfo == null) {
                        log.error("用户未注册，获取不到用户信息，用户id={}", userId);
                        return ResultVOUtil.error(-100, "用户未注册，获取不到用户信息");
                    }
                    NovelShelf novelShelf = shelfService.selectByUserId(userId);
                    if (novelShelf == null) {
                        log.error("用户书架不存在，获取不到用户书架信息，用户id={}", userId);
                        return ResultVOUtil.error(-101, "用户书架不存在，获取不到用户书架信息");
                    }
                    NovelShelfDetail novelShelfDetail = NovelShelfDetail.builder().build();
                    novelShelfDetail.setShelfDetailId(UUIDUtil.gen32UUID());//设置详情id
                    String shelfId = novelShelf.getShelfId();//获取书架id
                    novelShelfDetail.setUserId(userId);//设置用户id
                    novelShelfDetail.setShelfId(shelfId);//设置书架id
                    if (shelfMap.containsKey("novelId")) {
                        novelShelfDetail.setNovelId((String) shelfMap.get("novelId"));//设置小说id
                    }
                    if (shelfMap.containsKey("lastReadChapterId")) {
                        novelShelfDetail.setLastReadChapterId(String.valueOf(shelfMap.get("lastReadChapterId")));//设置最后一次阅读的章节的id(可能为空)。
                    }
                    if (shelfMap.containsKey("lastReadChapterIndex")) {
                        novelShelfDetail.setLastReadChapterIndex((Integer) shelfMap.get("lastReadChapterIndex"));
                    }
                    if (shelfMap.containsKey("lastReadPage")) {
                        novelShelfDetail.setLastReadPage((Integer) shelfMap.get("lastReadPage"));
                    }
                    if (shelfMap.containsKey("hasUpdate")) {
                        novelShelfDetail.setHasUpdate((Integer) shelfMap.get("hasUpdate"));
                    }
                    if (shelfMap.containsKey("isOrNotTop")) {
                        novelShelfDetail.setIsOrNotTop((Integer) shelfMap.get("isOrNotTop"));
                    }
                    if (shelfMap.containsKey("isOrNotPush")) {
                        novelShelfDetail.setIsOrNotPush((Integer) shelfMap.get("isOrNotPush"));
                    }
                    if (shelfMap.containsKey("sort")) {
                        novelShelfDetail.setSort((Integer) shelfMap.get("sort"));
                    }
                    if (shelfMap.containsKey("lastChapterUpdateTime")) {
                        String lastChapterUpdateTime_ = String.valueOf(shelfMap.get("lastChapterUpdateTime"));
                        Date lastChapterUpdateTime = DateUtil.fomatDateTimeStamp(lastChapterUpdateTime_);
                        novelShelfDetail.setLastChapterUpdateTime(lastChapterUpdateTime);
                    }
                    if (shelfMap.containsKey("lastReadTime")) {
                        String lastReadTime_ = String.valueOf(shelfMap.get("lastReadTime"));
                        Date lastReadTime = DateUtil.fomatDateTimeStamp(lastReadTime_);
                        novelShelfDetail.setLastReadTime(lastReadTime);
                    }
                    detailService.save(novelShelfDetail);
                }
            }
            return ResultVOUtil.success(0, "将小说添加到用户书架成功");
        } catch (Exception e) {
            log.error("将小说添加到用户书架出现错误", e);
            return ResultVOUtil.error(-102, "将小说添加到用户书架失败");
        } finally {
        	if (log.isDebugEnabled()) {
                log.info("end======================将小说添加到用户书架======================end");
            }
        }
    }

    /**
     * <pre>
     * removeFromShelf方法的作用：将小说从书架移除
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     * @param userId 用户id
     * @param novelId 小说id
     * @author Jack
     * @since JDK 1.6
     * </pre>
     */
	@GetMapping(value = "/remove")
    public ResultVO removeFromShelf(@RequestParam(name = "userId") String userId, @RequestParam(name = "novelId") String novelId) {
		try {
		 if (log.isDebugEnabled()) {
			 log.debug("start====================将小说从书架移除====================start");
		 }
		 if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(novelId)) {
			 log.error("用户id，小说id不能为空，获取不到用户的书架信息");
             return ResultVOUtil.error(-103, "用户id，小说id不能为空，获取不到用户的书架信息");
		}
		 NovelUserInfo novelUserInfo = userInfoService.findByUserId(userId);
         if (novelUserInfo == null) {
             log.error("用户未注册，获取不到用户信息，用户id={}", userId);
             return ResultVOUtil.error(-100, "用户未注册，获取不到用户信息");
         }
         NovelInfo novelInfo = novelInfoService.findById(novelId);
         if (novelInfo == null) {
        	 log.error("小说不存在，获取不到用户添加到书架的小说信息，小说id={}", novelId);
             return ResultVOUtil.error(-104, "小说不存在，获取不到用户添加到书架的小说信息");
		}
        NovelShelf novelShelf = shelfService.selectByUserId(userId);
        if (novelShelf == null) {
        	log.error("用户书架不存在，获取不到用户添加到书架的小说信息，用户id={}", userId);
            return ResultVOUtil.error(-105, "用户书架不存在，获取不到用户添加到书架的小说信息");
		}
        String shelfId = novelShelf.getShelfId();
        NovelShelfDetail shelfDetail = detailService.selectByUserIdAndShelfIdAndNovelId(userId, shelfId, novelId);
        if (shelfDetail == null) {
        	log.error("用户书架详情不存在，获取不到用户添加到书架的书架详情信息，用户id={}，书架id={}，小说id={}", userId, shelfId, novelId);
            return ResultVOUtil.error(-106, "用户书架详情不存在，获取不到用户添加到书架的书架详情信息");
		}
        String shelfDetailId = shelfDetail.getShelfDetailId();
        NovelShelfDetailIdClass idClass = NovelShelfDetailIdClass.builder().build();
        idClass.setShelfId(shelfId);
        idClass.setShelfDetailId(shelfDetailId);
        idClass.setNovelId(novelId);
        idClass.setUserId(userId);
        detailService.delete(idClass);
		return ResultVOUtil.success(0, "将小说从用户书架移除成功");
	 } catch (Exception e) {
		 log.error("将小说从书架移除出现错误", e);
		 return ResultVOUtil.error(-1, "将小说从用户书架移除失败");
	 } finally {
		 if (log.isDebugEnabled()) {
			 log.debug("end======================将小说从书架移除======================end");
		 }
	   }
    } 
}
