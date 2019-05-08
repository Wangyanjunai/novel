package com.potato369.novel.basic.repository.mapper;

import com.potato369.novel.basic.dataobject.NovelChapter;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository.mapper
 * @ClassName ChapterMapper
 * @Desc mapper
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/29 17:26
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface ChapterMapper {

    List<NovelChapter> selectByNovelId(String novelId);
    
    NovelChapter selectByChapterId(String chapterId);
}
