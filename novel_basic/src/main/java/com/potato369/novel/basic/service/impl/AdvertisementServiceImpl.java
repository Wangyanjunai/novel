package com.potato369.novel.basic.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.potato369.novel.basic.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.potato369.novel.basic.dataobject.NovelAdvertisement;
import com.potato369.novel.basic.repository.mapper.AdvertisementMapper;
import com.potato369.novel.basic.service.AdvertisementService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName AdvertisementServiceImpl
 * @Desc AdvertisementServiceImpl
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/25 11:27
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
	private AdvertisementRepository repository;
    
    @Autowired
    private AdvertisementMapper mapper;

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#save(com.potato369.novel.basic.dataobject.NovelAdvertisement)
	 * </pre>
	 */
	@Override
	public NovelAdvertisement save(NovelAdvertisement advertisement) {
		return repository.save(advertisement);
	}
	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#delete(java.lang.String)
	 * </pre>
	 */
	@Override
	public void delete(String adId) {
        repository.delete(adId);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#update(com.potato369.novel.basic.dataobject.NovelAdvertisement)
	 * </pre>
	 */
	@Override
	public NovelAdvertisement update(NovelAdvertisement advertisement) {
		return repository.saveAndFlush(advertisement);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#findByAdId(java.lang.String)
	 * </pre>
	 */
	@Override
	public NovelAdvertisement findByAdId(String adId) {
		return repository.findOne(adId);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#findAll()
	 * </pre>
	 */
	@Override
	public List<NovelAdvertisement> findAll() {
		return repository.findAll();
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：排序
	 * @see com.potato369.novel.basic.service.AdvertisementService#findAll()
	 * </pre>
	 */
	@Override
	public List<NovelAdvertisement> findAll(Sort sort) {
		return repository.findAll(sort);
	}
	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#findAll(org.springframework.data.domain.Pageable)
	 * </pre>
	 */
	@Override
	public Page<NovelAdvertisement> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#findByTaglimitSize(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 * </pre>
	 */
	@Override
	public List<NovelAdvertisement> findByTaglimitSize(Integer tag1, Integer tag2, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tag1", tag1);
		map.put("tag2", tag2);
		map.put("size", size);
		return mapper.selectAllByTag1AndTag2(map);
	}

	/**
	 * <pre>
	 * 描述该方法的实现功能：
	 * @see com.potato369.novel.basic.service.AdvertisementService#findByTagAndParentTypeIdLimitSize(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String)
	 * </pre>
	 */
	@Override
	public List<NovelAdvertisement> findByTagAndParentTypeIdLimitSize(Integer tag1, Integer tag2, Integer size, String parentTypeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tag1", tag1);
		map.put("tag2", tag2);
		map.put("size", size);
		map.put("parentTypeId", parentTypeId);
		return mapper.selectAllByTag1AndTag2AndParentTypeId(map);
	}
}
