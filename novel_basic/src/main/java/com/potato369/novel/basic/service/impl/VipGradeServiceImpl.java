package com.potato369.novel.basic.service.impl;

import com.potato369.novel.basic.dataobject.NovelVipGrade;
import com.potato369.novel.basic.repository.VipGradeRepository;
import com.potato369.novel.basic.service.VipGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service.impl
 * @ClassName VipGradeServiceImpl
 * @Desc VIP等级信息Service实现
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 18:15
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Service
@Transactional
public class VipGradeServiceImpl implements VipGradeService {

    @Autowired
    private VipGradeRepository repository;

    /**
     * <pre>
     * 新增VIP等级信息
     * @param novelVipGrade
     * @return NovelVipGrade
     * </pre>
     */
    @Override
    public NovelVipGrade save(NovelVipGrade novelVipGrade) {
        return repository.save(novelVipGrade);
    }

    /**
     * <pre>
     * 删除VIP等级信息
     * @param gradeId
     * </pre>
     */
    @Override
    public void delete(String gradeId) {
        repository.delete(gradeId);
    }

    /**
     * <pre>
     * 修改VIP等级信息
     * @param novelVipGrade
     * @return NovelVipGrade
     * </pre>
     */
    @Override
    public NovelVipGrade update(NovelVipGrade novelVipGrade) {
        return repository.saveAndFlush(novelVipGrade);
    }

    /**
     * <pre>
     * 根据主键查找一个VIP等级信息
     * @param primaryKey
     * @return NovelVipGrade
     * </pre>
     */
    @Override
    public NovelVipGrade findOne(String primaryKey) {
        return repository.findOne(primaryKey);
    }

    /**
     * <pre>
     * 查找所有的VIP等级信息
     * @return List<NovelVipGrade>
     * </pre>
     */
    @Override
    public List<NovelVipGrade> findAll() {
        return repository.findAll();
    }

    /**
     * <pre>
     * 分页查找所有的VIP等级信息
     * @return Page<NovelVipGrade>
     * </pre>
     *
     * @param pageable
     */
    @Override
    public Page<NovelVipGrade> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
