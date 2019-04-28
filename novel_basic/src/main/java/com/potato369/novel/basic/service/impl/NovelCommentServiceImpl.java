package com.potato369.novel.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.potato369.novel.basic.dataobject.NovelComment;
import com.potato369.novel.basic.repository.NovelCommentRepository;
import com.potato369.novel.basic.service.NovelCommentService;

/**
 * <pre>
 * 类名: NovelCommentServiceImpl
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
public class NovelCommentServiceImpl implements NovelCommentService{

	@Autowired
	private NovelCommentRepository repository;
	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelCommentService#save(com.potato369.novel.basic.dataobject.NovelComment)
	 * </pre>
	 */
		
	@Override
	public NovelComment save(NovelComment novelComment) {
		return repository.save(novelComment);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelCommentService#delete(java.lang.String)
	 * </pre>
	 */
		
	@Override
	public void delete(String commentId) {
		repository.delete(commentId);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelCommentService#update(com.potato369.novel.basic.dataobject.NovelComment)
	 * </pre>
	 */
		
	@Override
	public NovelComment update(NovelComment novelComment) {
		return repository.saveAndFlush(novelComment);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelCommentService#findOne(java.lang.String)
	 * </pre>
	 */
		
	@Override
	public NovelComment findOne(String commentId) {
		return repository.findOne(commentId);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelCommentService#findAll(org.springframework.data.domain.Sort)
	 * </pre>
	 */
		
	@Override
	public List<NovelComment> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.NovelCommentService#findAll(org.springframework.data.domain.Pageable, org.springframework.data.domain.Sort)
	 * </pre>
	 */
		
	@Override
	public Page<NovelComment> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
