package com.potato369.novel.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.potato369.novel.basic.dataobject.NovelComment;

/**
 * <pre>
 * 类名: CommentService
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月28日 下午5:21:38
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
public interface CommentService {
	
	/**
	 * 
	 * <pre>
	 * save方法的作用：新增评论
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param novelComment
	 * @return
	 * @since JDK 1.6
	 * </pre>
	 */
	@Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	NovelComment save(NovelComment novelComment); 
	
	/**
	 * 
	 * <pre>
	 * delete方法的作用：删除评论
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param commentId
	 * @since JDK 1.6
	 * </pre>
	 */
	@Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void delete(String commentId);
	
	/**
	 * 
	 * <pre>
	 * update方法的作用：更新评论
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 * @author Jack
	 * @param novelComment
	 * @return
	 * @since JDK 1.6
	 * </pre>
	 */
	@Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	NovelComment update(NovelComment novelComment);
	
	/**
	 * 
	 * <pre>
	 * findOne方法的作用：根据评论id查询一条评论信息
	 * 描述方法适用条件：
	 * 描述方法的执行流程：
	 * 描述方法的使用方法：
	 * 描述方法的注意事项：
	 *
	 * @author Jack
	 * @param commentId
	 * @return
	 * @since JDK 1.6
	 * </pre>
	 */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	NovelComment findOne(String commentId);
    
    /**
     * 
     * <pre>
     * findAll方法的作用：排序查询评论信息列表
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @param sort
     * @return
     * @since JDK 1.6
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    List<NovelComment> findAll(Sort sort);
    
    /**
     * 
     * <pre>
     * findAll方法的作用：分页排序查询评论信息列表
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @param pageable
     * @param sort
     * @return
     * @since JDK 1.6
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    Page<NovelComment> findAll(Pageable pageable);
}
