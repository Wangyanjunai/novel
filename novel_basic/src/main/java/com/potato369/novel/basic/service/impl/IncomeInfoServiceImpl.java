package com.potato369.novel.basic.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.potato369.novel.basic.dataobject.IncomeInfo;
import com.potato369.novel.basic.repository.IncomeInfoRepository;
import com.potato369.novel.basic.service.IncomeInfoService;

/**
 * <pre>
 * 类名: IncomeInfoServiceImpl
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年6月25日 上午11:10:37
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */
@Service
@Transactional
public class IncomeInfoServiceImpl implements IncomeInfoService {

	@Autowired
	private IncomeInfoRepository repository;
	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#save(com.potato369.novel.basic.dataobject.IncomeInfo)
	 * </pre>
	 */

	@Override
	public IncomeInfo save(IncomeInfo incomeInfo) {
		
		return repository.save(incomeInfo);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#delete(java.lang.String)
	 * </pre>
	 */

	@Override
	public void delete(String incomeId) {
		repository.delete(incomeId);

	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#update(com.potato369.novel.basic.dataobject.IncomeInfo)
	 * </pre>
	 */

	@Override
	public IncomeInfo update(IncomeInfo incomeInfo) {
		return repository.saveAndFlush(incomeInfo);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#findByAdId(java.lang.String)
	 * </pre>
	 */

	@Override
	public IncomeInfo findByAdId(String incomeId) {
		return repository.findOne(incomeId);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#findAll()
	 * </pre>
	 */

	@Override
	public List<IncomeInfo> findAll() {
		return repository.findAll();
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#findAll(org.springframework.data.domain.Sort)
	 * </pre>
	 */

	@Override
	public List<IncomeInfo> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#findAll(org.springframework.data.domain.Pageable)
	 * </pre>
	 */

	@Override
	public Page<IncomeInfo> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.IncomeInfoService#get7DaysIncomeAmount(java.lang.String, java.util.Date, java.util.Date)
	 * </pre>
	 */
		
	@Override
	public BigDecimal get7DaysIncomeAmount(String userId, Date start, Date end) {
		return repository.get7DaysIncomeAmount(userId, start, end);
	}

}
