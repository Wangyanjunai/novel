package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.NovelUserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName UserAccountService
 * @Desc 用户账户信息Service
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 18:18
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface UserAccountService {

    /**
     * <pre>
     * 新增用户账户信息
     * @param novelUserAccount
     * @return NovelUserAccount
     * </pre>
     */
    NovelUserAccount save(NovelUserAccount novelUserAccount);

    /**
     * <pre>
     * 删除用户账户信息
     * @param primaryKey
     * </pre>
     */
    void delete(String primaryKey);

    /**
     * <pre>
     * 更新用户账户信息
     * @param novelUserAccount
     * </pre>
     */
    NovelUserAccount update(NovelUserAccount novelUserAccount);

    /**
     * <pre>
     * 根据主键查找用户账户信息
     * @return novelUserAccount
     * </pre>
     */
    NovelUserAccount findOne(String primaryKey);

    /**
     * <pre>
     * 根据用户mid和用户绑定的账号名称查找用户绑定的账户信息
     * @param userId
     * @param accountName
     * @return Page<NovelUserAccount>
     * </pre>
     */
    NovelUserAccount findByUserIdAndAccountName(String userId, String accountName);

    /**
     * <pre>
     * 查找所有的用户账户信息列表
     * @return List<NovelUserAccount>
     * </pre>
     */
    List<NovelUserAccount> findAll();

    /**
     * <pre>
     * 分页查找所有的用户账户信息列表
     * @param pageable
     * @return Page<NovelUserAccount>
     * </pre>
     */
    Page<NovelUserAccount> findAll(Pageable pageable);
}
