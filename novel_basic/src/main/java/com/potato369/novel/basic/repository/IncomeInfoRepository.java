package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.IncomeInfo;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic.repository
 * @InterfaceName IncomeInfoRepository
 * @Desc 收益记录数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/6/25 10:58
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Repository
public interface IncomeInfoRepository extends JpaRepository<IncomeInfo, String> {

    @Query("SELECT sum(ic.incomeAmount) FROM IncomeInfo ic WHERE ic.incomeTime >=?2 AND incomeTime <=?3 AND ic.userId = ?1 GROUP BY ic.userId")
    BigDecimal get7DaysIncomeAmount(String userId, Date start, Date end);
}
