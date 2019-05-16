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
    NovelInfo findById(String id);
    
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

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findByNovelStatus(Integer novelStatus);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findAll();

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Integer getCategoryCount(String category_en_text);
    
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findByCategoryEnText(String categoryENText);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelInfo> findByCategoryType(Integer type);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findByCategoryType(Pageable pageable, Integer type);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findAll(Pageable pageable);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findNovelInfoByCategoryTypeIn(Pageable pageable, List<Integer> categoryTypes);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findAllByTitleLikeOrAuthorLike(Pageable pageable, String keyWords);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findByNovelStatusAndCategoryTypeIn(Integer novelStatus, Pageable pageable, List<Integer> categoryTypes);

    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelInfo> findByAuthorContainsOrTitleContains(String keyWords, Pageable pageable);
}
