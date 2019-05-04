package com.potato369.novel.basic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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
 * @CreateBy IntellJ IDEA 2018.3.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface NovelInfoService {

    /**
     * <pre>
     * 新增小说信息到数据库
     * @param novelInfo 小说信息实体对象
     * @return NovelInfo.class
     * </pre>
     */
	@Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    NovelInfo save(NovelInfo novelInfo);

    /**
     * <pre>
     * 从数据库删除小说信息
     * @param id 小说id
     * </pre>
     */
	@Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void delete(String id);

    /**
     * <pre>
     * 修改数据库小说信息
     * @param novelInfo 小说信息实体对象
     * @return NovelInfo.class
     * </pre>
     */
	@Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    NovelInfo update(NovelInfo novelInfo);

    /**
     * 根据id查找单个小说
     * @param id
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelInfo find(String id);
    
    /**
     * <pre>
     * 根据小说名称和分类查找小说信息
     * @param title 小说名称
     * @param categoryText 小说分类中文名称
     * @return NovelInfo.class
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelInfo findByTitleAndCategoryText(String title, String categoryText);
    
    /**
     * <pre>
     * 根据小说名称和分类查找小说信息
     * @param title 小说名称
     * @param categoryText 小说分类中文名称
     * @param author 小说作者
     * @return NovelInfo.class
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelInfo findByTitleAndCategoryTextAndAuthor(String title, String categoryText, String author);

    /**
     * 根据小说的状态查询小说信息列表
     * @param novelStatus
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findByNovelStatus(Integer novelStatus);

    /**
     * 查找小说列表
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findAll();
    
    /**
     * <pre>
     * getCategoryCount方法的作用：
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     * @author Jack
     * @param category_en_text
     * @return
     * @since JDK 1.6
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Integer getCategoryCount(String category_en_text);
    
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findByCategoryEnText(String categoryENText);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findByCategoryType(Integer type);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findByCategoryType(Pageable pageable, Integer type);
    
    /**
     * 查找小说分页列表
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findAll(Pageable pageable);
}
