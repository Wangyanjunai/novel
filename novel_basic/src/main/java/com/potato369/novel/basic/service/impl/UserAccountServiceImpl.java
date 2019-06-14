package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelUserAccount;
import com.potato369.novel.basic.repository.UserAccountRepository;
import com.potato369.novel.basic.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName UserAccountServiceImpl
 * @Desc 用户账户信息Service实现
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 18:27
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository repository;

    /**
     * <pre>
     * 新增用户账户信息
     * @param novelUserAccount
     * @return NovelUserAccount
     * </pre>
     */
    @Override
    public NovelUserAccount save(NovelUserAccount novelUserAccount) {
        return repository.save(novelUserAccount);
    }

    /**
     * <pre>
     * 删除用户账户信息
     * @param primaryKey
     * </pre>
     */
    @Override
    public void delete(String primaryKey) {
        repository.delete(primaryKey);
    }

    /**
     * <pre>
     * 更新用户账户信息
     * @param novelUserAccount
     * </pre>
     */
    @Override
    public NovelUserAccount update(NovelUserAccount novelUserAccount) {
        return repository.saveAndFlush(novelUserAccount);
    }

    /**
     * <pre>
     * 更新用户账户信息
     * @return novelUserAccount
     * </pre>
     *
     * @param primaryKey
     */
    @Override
    public NovelUserAccount findOne(String primaryKey) {
        return repository.findOne(primaryKey);
    }
    
    /**
     * <pre>
     * 根据用户mid查找用户绑定的账户信息
     * @param userId
     * @return NovelUserAccount
     * </pre>
     */
    @Override
    public NovelUserAccount findByUserId(String userId) {
        return repository.selectByUserId(userId);
    }

    /**
     * <pre>
     * 根据用户mid和用户绑定的账号名称查找用户绑定的账户信息
     * @param userId
     * @param accountName
     * @return Page<NovelUserAccount>
     * </pre>
     */
    @Override
    public NovelUserAccount findByUserIdAndAccountName(String userId, String accountName) {
        return repository.selectByUserIdAndAccountName(userId, accountName);
    }

    /**
     * <pre>
     * 查找所有的用户账户信息列表
     * @return List<NovelUserAccount>
     * </pre>
     */
    @Override
    public List<NovelUserAccount> findAll() {
        return repository.findAll();
    }

    /**
     * <pre>
     * 分页查找所有的用户账户信息列表
     * @param pageable
     * @return Page<NovelUserAccount>
     * </pre>
     */
    @Override
    public Page<NovelUserAccount> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
