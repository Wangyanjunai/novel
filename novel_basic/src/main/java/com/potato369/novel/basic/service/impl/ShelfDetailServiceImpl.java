package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelShelfDetail;
import com.potato369.novel.basic.dataobject.idClass.NovelShelfDetailIdClass;
import com.potato369.novel.basic.repository.ShelfDetailRepository;
import com.potato369.novel.basic.service.ShelfDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName ShelfDetailServiceImpl
 * @Desc ShelfDetailService
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/16 18:18
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
public class ShelfDetailServiceImpl implements ShelfDetailService {

    @Autowired
    private ShelfDetailRepository detailRepository;

    @Override
    public List<NovelShelfDetail> selectByUserIdAndShelfId(String userId, String shelfId) {
        return detailRepository.selectByUserIdAndShelfId(userId, shelfId);
    }

    @Override
    public NovelShelfDetail save(NovelShelfDetail novelShelfDetail) {
        return detailRepository.save(novelShelfDetail);
    }
    
    @Override
    public NovelShelfDetail selectByUserIdAndShelfIdAndNovelId(String userId, String shelfId, String novelId) {
    	return detailRepository.selectByUserIdAndShelfIdAndNovelId(userId, shelfId, novelId);
    }
		
	@Override
	public void delete(NovelShelfDetailIdClass idClass) {
		detailRepository.delete(idClass);
	}
}
