package com.potato369.novel.basic.service;

import cn.wanghaomiao.seimi.struct.Response;
import com.potato369.novel.basic.dataobject.NovelChapter;
import com.potato369.novel.basic.dataobject.NovelInfo;
import com.potato369.novel.basic.model.NovelChapterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName NovelChapterService
 * @Desc NovelChapterService，小说章节信息service接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/3/28 13:54
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface NovelChapterService {

    /**
     * <pre>
     * 新增小说章节信息
     * @param novelChapter
     * @return NovelChapter.class
     * </pre>
     */
    NovelChapter save(NovelChapter novelChapter);

    /**
     * <pre>
     * 新增小说章节信息
     * @param response
     * @return NovelChapter.class
     * </pre>
     */
    NovelChapter save(Response response, String bookId);

    /**
     * <pre>
     * 根据小说chapterId删除小说章节信息
     * @param chapterId
     * </pre>
     */
    void delete(String chapterId);

    /**
     * <pre>
     * 更新小说章节信息
     * @param novelChapter
     * @return NovelChapter.class
     * </pre>
     */
    NovelChapter update(NovelChapter novelChapter);

    /**
     * <pre>
     * 根据chapterId主键查找小说章节信息
     * @param chapterId
     * @return NovelChapter.class
     * </pre>
     */
    NovelChapter findNovelChapterByChapterId(String chapterId);

    /**
     * <pre>
     * 根据chapterId主键查找小说章节信息
     * @param chapterId
     * @return NovelChapter.class
     * </pre>
     */
    NovelInfo findNovelInfoByChapterId(String chapterId);

    /**
     * <pre>
     * 查找bookid对应的一本小说的所有的章节信息
     * @return NovelChapter.class
     * </pre>
     */
    List<NovelChapter> findAll(String bookId);

    /**
     * <pre>
     * 查找bookid对应的一本小说的所有的章节信息
     * @return NovelChapter.class
     * </pre>
     */
    Page<NovelChapterModel> findAll(String bookId, Pageable pageable);

    /**
     * <pre>
     * findByChaperTitle方法的作用：根据章节标题查询章节信息
     * @author Jack
     * @param title
     * @return
     * @since JDK 1.8
     * </pre>
     */
    List<NovelChapter> findByChaperTitle(String title);

    /**
     * <pre>
     * findByChaperTitleAndBookId方法的作用：根据章节标题查询章节信息
     * @author Jack
     * @param title 小说章节名称
     * @param bookId 小说id
     * @return
     * @since JDK 1.8
     * </pre>
     */
    NovelChapter findChaperByTitleAndBookId(String title, String bookId);

    List<NovelChapter> selectByNovelId(String novelId);

    /**
     * <pre>
     * selectByNovelIdAndChapterId方法的作用：根据小说id和章节id获取小说内容
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @param novelId
     * @param index
     * @return
     * @since JDK 1.6
     * </pre>
     */
    NovelChapter selectByNovelIdAndIndex(String novelId, Integer index);

    Page<NovelChapterModel> findAllByNovelId(String novelId, Pageable pageable);

    List<NovelChapter> findAllContentByNovelId(String novelId);
}
