package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @InterfaceName NovelInfoRepository
 * @Desc 小说信息记录Repository数据库操作类
 * @WebSite https://www.potato369.com
 * @Author 王艳军
 * @Date 2018/12/14 13:37
 * @CreateBy IntellJ IDEA 2018.2.6
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface NovelInfoRepository extends JpaRepository<NovelInfo, String> {

    List<NovelInfo> findNovelInfoByNovelStatus(Integer novelStatus);

    List<NovelInfo> findNovelInfoByCategoryType(Integer categoryType);

    Page<NovelInfo> findNovelInfoByCategoryType(Pageable pageable, Integer type);
    
    NovelInfo findNovelInfoByTitleAndCategoryCNText(String title, String categoryCNText);
    
    List<NovelInfo> findByCategoryENText(String categoryENText);

    Integer findCountByCategoryENText(String categoryENText);
}
