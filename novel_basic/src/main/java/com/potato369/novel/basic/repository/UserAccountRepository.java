package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.NovelUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @InterfaceName UserAccountRepository
 * @Desc 用户账户信息数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 17:20
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface UserAccountRepository extends JpaRepository<NovelUserAccount, String> {

    @Query("select nu from NovelUserAccount nu where nu.userId = ?1 and nu.accountName = ?2")
    NovelUserAccount selectByUserIdAndAccountName(String userId, String accountName);
}
