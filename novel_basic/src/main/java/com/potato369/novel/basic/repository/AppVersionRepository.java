package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.AppVersion;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @ClassName AppVersionRepository
 * @Desc
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/5/20 17:47
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface AppVersionRepository extends JpaRepository<AppVersion, String> {
}
