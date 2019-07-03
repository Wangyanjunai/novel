package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelMenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @InterfaceName MenuInfoRepository
 * @Desc 公众号菜单信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 17:26
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Repository
public interface MenuInfoRepository extends JpaRepository<NovelMenuInfo, String> {
}
