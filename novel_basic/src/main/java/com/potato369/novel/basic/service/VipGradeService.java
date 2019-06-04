package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.NovelVipGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName VipGradeService
 * @Desc VIP等级信息Service
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/4 18:06
 * @CreateBy IntellJ IDEA 2019.1.1
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface VipGradeService {

    /**
     * <pre>
     * 新增VIP等级信息
     * @param novelVipGrade
     * @return NovelVipGrade
     * </pre>
     */
    NovelVipGrade save(NovelVipGrade novelVipGrade);

    /**
     * <pre>
     * 删除VIP等级信息
     * @param gradeId
     * </pre>
     */
    void delete(String gradeId);

    /**
     * <pre>
     * 修改VIP等级信息
     * @param novelVipGrade
     * @return NovelVipGrade
     * </pre>
     */
    NovelVipGrade update(NovelVipGrade novelVipGrade);

    /**
     * <pre>
     * 根据主键查找一个VIP等级信息
     * @param primaryKey
     * @return NovelVipGrade
     * </pre>
     */
    NovelVipGrade findOne(String primaryKey);

    /**
     * <pre>
     * 查找所有的VIP等级信息
     * @return List<NovelVipGrade>
     * </pre>
     */
    List<NovelVipGrade> findAll();

    /**
     * <pre>
     * 分页查找所有的VIP等级信息
     * @return Page<NovelVipGrade>
     * </pre>
     */
    Page<NovelVipGrade> findAll(Pageable pageable);
}
