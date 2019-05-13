package com.potato369.novel.basic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.potato369.novel.basic.dataobject.NovelChapter;
import org.springframework.data.jpa.repository.Query;

/**
 * <pre>
 * 类名: NovelChapterRepository
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年3月27日 上午10:22:42
 * 描述: 
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
}
