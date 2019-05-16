package com.potato369.novel.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.potato369.novel.basic.dataobject.NovelComment;

/**
 * <pre>
 * 类名: CommentRepository
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月28日 下午5:20:36
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */

public interface CommentRepository extends JpaRepository<NovelComment, String>{
}
