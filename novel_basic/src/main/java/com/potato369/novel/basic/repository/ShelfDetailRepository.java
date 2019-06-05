package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelShelfDetail;
import com.potato369.novel.basic.dataobject.idClass.NovelShelfDetailIdClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.dataobject
 * @InterfaceName ShelfDetailRepository
 * @Desc 书架详情信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 17:16
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface ShelfDetailRepository extends JpaRepository<NovelShelfDetail, NovelShelfDetailIdClass> {

    @Query(value = "SELECT nd FROM NovelShelfDetail nd WHERE nd.userId = ?1 AND nd.shelfId = ?2")
    List<NovelShelfDetail> selectByUserIdAndShelfId(String userId, String shelfId);
    
    @Query(value = "SELECT nd FROM NovelShelfDetail nd WHERE nd.userId = ?1 AND nd.shelfId = ?2 AND novelId = ?3")
    NovelShelfDetail selectByUserIdAndShelfIdAndNovelId(String userId, String shelfId, String novelId);
}
