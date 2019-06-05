package com.potato369.novel.basic.repository;

import com.potato369.novel.basic.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/**
 * <pre>
 * @PackageName com.potato369.novel.dataobject
 * @InterfaceName OrderDetailRepository
 * @Desc 订单详情数据操作Repository接口
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/08 18:10
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);
}
