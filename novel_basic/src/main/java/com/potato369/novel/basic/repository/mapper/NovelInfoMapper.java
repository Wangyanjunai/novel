package com.potato369.novel.basic.repository.mapper;

import com.potato369.novel.basic.dataobject.NovelInfo;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository.mapper
 * @IntellfaceName NovelInfoMapper
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-05-09 21:30
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface NovelInfoMapper {
    List<NovelInfo> findAllDataUrl();
}
