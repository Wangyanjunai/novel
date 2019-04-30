package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.NovelAdvertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName AdvertisementService
 * @Desc AdvertisementService
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/25 10:59
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface AdvertisementService {

    /**
     * <pre>
     * 新增广告信息
     * @param advertisement
     * @return
     * </pre>
     */
    @Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    NovelAdvertisement save(NovelAdvertisement advertisement);

    /**
     * <pre>
     * 根据广告id删除广告信息
     * @param adId
     * </pre>
     */
    @Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void delete(String adId);

    /**
     * <pre>
     * 更新广告信息
     * @param advertisement
     * @return
     * </pre>
     */
    @Modifying
    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    NovelAdvertisement update(NovelAdvertisement advertisement);

    /**
     * <pre>
     * 根据广告id主键查找广告信息
     * @param adId
     * @return
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    NovelAdvertisement findByAdId(String adId);

    /**
     * <pre>
     * 查找所有的广告信息
     * @return
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelAdvertisement> findAll();

    /**
     * <pre>
     * 查找所有的广告信息排序
     * @return
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelAdvertisement> findAll(Sort sort);
    
    /**
     * 
     * <pre>
     * findByTaglimitSize方法的作用：
     * 描述方法适用条件：
     * 描述方法的执行流程：
     * 描述方法的使用方法：
     * 描述方法的注意事项：
     *
     * @author Jack
     * @return
     * @since JDK 1.6
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    List<NovelAdvertisement> findByTaglimitSize(Integer tag1, Integer tag2, Integer size);

    /**
     * <pre>
     * 分页查找所有的广告信息
     * @param pageable
     * @return
     * </pre>
     */
    @Transactional(readOnly = true, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    Page<NovelAdvertisement> findAll(Pageable pageable);
    
    
}
