package com.potato369.novel.basic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.potato369.novel.basic.dataobject.NovelChapter;

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

	List<NovelChapter> findByBookId(Integer bookId);
}
