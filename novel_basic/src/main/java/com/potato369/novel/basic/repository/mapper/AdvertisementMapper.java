package com.potato369.novel.basic.repository.mapper;

import java.util.List;
import java.util.Map;

import com.potato369.novel.basic.dataobject.NovelAdvertisement;

/**
 * <pre>
 * 类名: AdvertisementMapper
 * 类的作用:
 * 创建原因:
 * 创建时间: 2019年4月30日 下午4:30:51
 * 描述: 
 * @author Jack
 * @version 
 * @since JDK 1.6
 * </pre>
 */

public interface AdvertisementMapper {

	List<NovelAdvertisement> selectAllByTag1AndTag2(Map<String, Object> map);
	
	List<NovelAdvertisement> selectAllByTag1AndTag2AndParentTypeId(Map<String, Object> map);
}
