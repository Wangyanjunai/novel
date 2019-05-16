package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelShelf;
import com.potato369.novel.basic.dataobject.idClass.NovelShelfIdClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @InterfaceName ShelfRepository
 * @Desc 小说书架Repository
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 15:04
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface ShelfRepository extends JpaRepository<NovelShelf, NovelShelfIdClass> {

    @Query(value = "select sh from NovelShelf sh where sh.userId = ?1", nativeQuery = false)
    NovelShelf selectByUserId(String userId);
}
