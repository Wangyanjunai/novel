package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.NovelShelfDetail;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName ShelfDetailService
 * @Desc ShelfDetailService
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 18:16
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface ShelfDetailService {

    List<NovelShelfDetail> selectByUserIdAndShelfId(String userId, String shelfId);
}
