package com.potato369.novel.app.web.controller;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.controller
 * @ClassName VersionService
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/20 17:53
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */

import com.potato369.novel.app.web.utils.ResultVOUtil;
import com.potato369.novel.app.web.vo.AppVersionVO;
import com.potato369.novel.app.web.vo.ResultVO;
import com.potato369.novel.basic.dataobject.AppVersion;
import com.potato369.novel.basic.service.AppVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/android/releases")
@Slf4j
public class VersionController {

    @Autowired
    private AppVersionService appVersionService;

    @GetMapping(value = "/latest")
    public ResultVO<AppVersionVO> latest() {
        try {
            AppVersionVO appVersionVO = AppVersionVO.builder().build();
            if (log.isDebugEnabled()) {
                log.debug("后台开始获取app最新版本信息");
            }
            Sort sort = new Sort(Sort.Direction.DESC, "versionCode", "publishTime");
            List<AppVersion> appVersionList = appVersionService.findAll(sort);
            if (appVersionList != null && !appVersionList.isEmpty() && appVersionList.size() > 0) {
                AppVersion appVersion = appVersionList.get(0);
                BeanUtils.copyProperties(appVersion, appVersionVO);
            }
            return ResultVOUtil.success(appVersionVO);
        } catch (Exception e) {
            log.error("获取最新版本失败", e);
            return ResultVOUtil.error();
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("后台结束获取app最新版本信息");
            }
        }
    }
}
