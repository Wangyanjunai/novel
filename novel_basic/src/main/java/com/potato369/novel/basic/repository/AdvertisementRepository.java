package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @InterfaceName AdvertisementRepository
 * @Desc Advertisement
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/25 10:58
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface AdvertisementRepository extends JpaRepository<NovelAdvertisement, String> {
}
