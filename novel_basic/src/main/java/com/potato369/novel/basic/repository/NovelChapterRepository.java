package com.potato369.novel.basic.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.potato369.novel.basic.dataobject.NovelChapter;
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
public interface NovelChapterRepository extends JpaRepository<NovelChapter, String>{

	List<NovelChapter> findByBookId(String bookId);
	
	List<NovelChapter> findByTitle(String title, Sort sort);
	
	NovelChapter findByTitleAndBookId(String title, String bookId, Sort sort);

	Page<NovelChapter> findAllByBookId(String bookId, Pageable pageable);
	
	@Query(value = "SELECT ch FROM NovelChapter ch WHERE ch.bookId = ?1 ORDER BY ch.index ASC")
	List<NovelChapter> selectByNovelId(String novelId);
}
