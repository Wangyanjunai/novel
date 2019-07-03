package com.potato369.novel.basic.repository;

import java.util.List;

import com.potato369.novel.basic.model.NovelChapterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.potato369.novel.basic.dataobject.NovelChapter;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * 类名: NovelChapterRepository
 * 类的作用:小说章节信息数据操作Repository接口
 * 创建原因:小说章节信息数据操作Repository接口
 * 创建时间: 2019年3月27日 上午10:22:42
 * 描述:小说章节信息数据操作Repository接口
 * @author Jack
 * @version
 * @since JDK 1.6
 * </pre>
 */
@Repository
public interface NovelChapterRepository extends JpaRepository<NovelChapter, String> {

    List<NovelChapter> findByBookId(String bookId);

    List<NovelChapter> findByTitle(String title, Sort sort);

    NovelChapter findByTitleAndBookId(String title, String bookId, Sort sort);

    @Query(value = "SELECT new com.potato369.novel.basic.model.NovelChapterModel(ch.id, ch.index, ch.title, ch.createTime) FROM NovelChapter ch WHERE ch.bookId=:bookId")
    Page<NovelChapterModel> findAllByBookId(@Param("bookId") String bookId, Pageable pageable);

    @Query(value = "SELECT ch FROM NovelChapter ch WHERE ch.bookId = ?1 ORDER BY ch.index ASC")
    List<NovelChapter> selectByNovelId(String novelId);
}
