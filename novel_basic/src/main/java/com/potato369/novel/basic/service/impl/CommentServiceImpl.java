package com.potato369.novel.basic.service.impl;

import java.util.List;

import com.potato369.novel.basic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.potato369.novel.basic.dataobject.NovelComment;
import com.potato369.novel.basic.repository.CommentRepository;

/**
 * <pre>
 * 类名: CommentServiceImpl
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月28日 下午5:30:14
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository repository;
	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see CommentService#save(com.potato369.novel.basic.dataobject.NovelComment)
	 * </pre>
	 */
		
	@Override
	public NovelComment save(NovelComment novelComment) {
		return repository.save(novelComment);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see CommentService#delete(java.lang.String)
	 * </pre>
	 */
		
	@Override
	public void delete(String commentId) {
		repository.delete(commentId);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see CommentService#update(com.potato369.novel.basic.dataobject.NovelComment)
	 * </pre>
	 */
		
	@Override
	public NovelComment update(NovelComment novelComment) {
		return repository.saveAndFlush(novelComment);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see CommentService#findOne(java.lang.String)
	 * </pre>
	 */
		
	@Override
	public NovelComment findOne(String commentId) {
		return repository.findOne(commentId);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see CommentService#findAll(org.springframework.data.domain.Sort)
	 * </pre>
	 */
		
	@Override
	public List<NovelComment> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see CommentService#findAll(org.springframework.data.domain.Pageable, org.springframework.data.domain.Sort)
	 * </pre>
	 */
		
	@Override
	public Page<NovelComment> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
