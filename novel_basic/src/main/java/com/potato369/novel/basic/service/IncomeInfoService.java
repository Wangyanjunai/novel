package com.potato369.novel.basic.service;

import com.potato369.novel.basic.dataobject.IncomeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.service
 * @InterfaceName IncomeInfoService
 * @Desc IncomeInfoService
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/25 10:59
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface IncomeInfoService {

    /**
     * <pre>
     * 新增收益记录信息
     * @param incomeInfo
     * @return
     * </pre>
     */
	IncomeInfo save(IncomeInfo incomeInfo);

    /**
     * <pre>
     * 根据收益记录id删除收益记录信息
     * @param incomeId
     * </pre>
     */
    void delete(String incomeId);

    /**
     * <pre>
     * 更新收益记录信息
     * @param incomeInfo
     * @return
     * </pre>
     */
    IncomeInfo update(IncomeInfo incomeInfo);

    /**
     * <pre>
     * 根据收益记录信息incomeId主键查找收益记录信息
     * @param adId
     * @return
     * </pre>
     */
    IncomeInfo findByAdId(String incomeId);
    
    /**
     * <pre>
     * 根据用户id查找用户最近7天的总收益
     * @param userId
     * @param start
     * @param end
     * @return
     * </pre>
     */
    BigDecimal get7DaysIncomeAmount(String userId, Date start, Date end);

    /**
     * <pre>
     * 查找所有的收益记录信息
     * @return
     * </pre>
     */
    List<IncomeInfo> findAll();

    /**
     * <pre>
     * 查找所有的收益记录信息排序
     * @return
     * </pre>
     */
    List<IncomeInfo> findAll(Sort sort);
    
    /**
     * <pre>
     * 分页查找所有的收益记录信息
     * @param pageable
     * @return
     * </pre>
     */
    Page<IncomeInfo> findAll(Pageable pageable);
}
