package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.HotWordsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName HotWordsInfoService
 * @Desc HotWordsInfoService
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/14 11:08
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface HotWordsInfoService {

    Page<HotWordsInfo> findAll(PageRequest pageRequest);

    String findByWord(String words);

    HotWordsInfo findByWordId(String wordId);

    HotWordsInfo save(HotWordsInfo hotWordsInfo);

    HotWordsInfo update(HotWordsInfo hotWordsInfo);
}
