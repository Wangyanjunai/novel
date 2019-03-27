package com.potato369.novel.basic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.potato369.novel.basic.dataobject.NovelInfo;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.service
 * @InterfaceName NovelInfoService
 * @Desc 小说信息业务Service接口
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/14 13:40
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface NovelInfoService {

    /**
     * 新增
     * @param novelInfo
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelInfo save(NovelInfo novelInfo);

    /**
     * 删除
     * @param id
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    void delete(Integer id);

    /**
     * 修改
     * @param novelInfo
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelInfo update(NovelInfo novelInfo);

    /**
     * 根据id查找单个小说
     * @param id
     * @return
     */
    NovelInfo find(Integer id);

    /**
     * 根据fileName查找小说详情信息
     * @param fileName
     * @return
     */
    NovelInfo findByFileName(String fileName);

    /**
     * 根据小说的状态查询小说信息列表
     * @param novelStatus
     * @return
     */
    List<NovelInfo> findByNovelStatus(Integer novelStatus);

    /**
     * 查找小说列表
     * @return
     */
    List<NovelInfo> findAll();

    /**
     * 查找小说分页列表
     * @param pageable
     * @return
     */
    Page<NovelInfo> findAll(Pageable pageable);
}
