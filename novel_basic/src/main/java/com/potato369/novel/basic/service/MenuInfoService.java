package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.NovelMenuInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName MenuInfoService
 * @Desc 公众号自定义菜单数据Service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/5 9:11
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface MenuInfoService {

    /**
     * <pre>
     * 新增公众号自定义菜单数据
     * @param menuInfo
     * @return NovelMenuInfo
     * </pre>
     */
    NovelMenuInfo save(NovelMenuInfo menuInfo);

    /**
     * <pre>
     * 根据主键删除公众号自定义菜单数据
     * @param primaryKey
     * </pre>
     */
    void delete(String primaryKey);

    /**
     * <pre>
     * 修改公众号自定义菜单数据
     * @param menuInfo
     * @return NovelMenuInfo
     * </pre>
     */
    NovelMenuInfo update(NovelMenuInfo menuInfo);

    /**
     * <pre>
     * 根据主键查找一个公众号自定义菜单数据
     * @param primaryKey
     * @return NovelMenuInfo
     * </pre>
     */
    NovelMenuInfo findOne(String primaryKey);

    /**
     * <pre>
     * 查找公众号自定义菜单数据列表
     * @return List<NovelMenuInfo>
     * </pre>
     */
    List<NovelMenuInfo> findAll();

    /**
     * <pre>
     * 分页查询公众号自定义菜单数据列表
     * @param pageable
     * @return Page<NovelMenuInfo>
     * </pre>
     */
    Page<NovelMenuInfo> findAll(Pageable pageable);
}
