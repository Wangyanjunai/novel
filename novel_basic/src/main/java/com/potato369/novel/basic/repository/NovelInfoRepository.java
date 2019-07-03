package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @InterfaceName NovelInfoRepository
 * @Desc 小说信息记录数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/14 13:37
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Repository
public interface NovelInfoRepository extends JpaRepository<NovelInfo, String> {

    List<NovelInfo> findNovelInfoByNovelStatus(Integer novelStatus);

    List<NovelInfo> findNovelInfoByCategoryType(Integer categoryType);

    Page<NovelInfo> findNovelInfoByCategoryType(Pageable pageable, Integer type);
    
    Page<NovelInfo> findNovelInfoByCategoryTypeIn(Pageable pageable, List<Integer> categoryTypes);
    
    NovelInfo findNovelInfoByTitleAndCategoryCNText(String title, String categoryCNText);
    
    NovelInfo findNovelInfoByTitleAndCategoryCNTextAndAuthor(String title, String categoryCNText, String author);
    
    List<NovelInfo> findByCategoryENText(String categoryENText);

    Integer findCountByCategoryENText(String categoryENText);

    Page<NovelInfo> findByTitleLikeOrAuthorLike(Pageable pageable, String word);

    Page<NovelInfo> findByNovelStatusAndCategoryTypeIn(Integer novelStatus, Pageable pageable, List<Integer> categoryTypes);

    @Query(value = "select n from NovelInfo n where 1 = 1 and n.title =?1 or n.author =?1 or (n.title like concat(concat('%', ?1), '%')) or (n.author like concat(concat('%', ?1), '%'))")
    Page<NovelInfo> findByAuthorOrTitle(String words, Pageable pageable);

    @Modifying
    @Query("update NovelInfo n set n.clickNumber=?1 where n.id=?2")
    void updateClickNumber(BigDecimal clickNumber, String novelId);
}
