package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 * @PackageName com.potato369.novel.repository
 * @InterfaceName OrderMasterRepository
 * @Desc 订单信息Repository数据库操作类
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 17:54
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * <pre>
     * 根据买家的微信Openid分页查询买家的订单信息
     * @param buyerOpenid
     * @param pageable
     * @return
     * </pre>
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

    /**
     * <pre>
     * 根据支付状态和订单状态查询订单信息
     * @param payStatus
     * @param orderStatus
     * @return
     * </pre>
     */
    List<OrderMaster> findOrderMastersByPayStatusAndOrderStatus(Integer payStatus, Integer orderStatus);
}
