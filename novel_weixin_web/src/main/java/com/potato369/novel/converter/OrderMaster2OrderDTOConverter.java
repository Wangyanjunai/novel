package com.potato369.novel.converter;

import com.potato369.novel.basic.dataobject.OrderMaster;
import com.potato369.novel.dto.OrderDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
/**
 * <pre>
 * @PackageName com.potato369.novel.converter
 * @ClassName OrderMaster2OrderDTOConverter
 * @Desc 将订单信息转换为订单DTO转化器
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/09 14:46
 * @CreateBy IntellJ IDEA 2018.3.2
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class OrderMaster2OrderDTOConverter {

    /**
     * <pre>
     * 将OrderMaster对象转换为OrderDTO对象
     * @param orderMaster
     * @return OrderDTO对象
     * </pre>
     */
    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = OrderDTO.builder().build();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    /**
     * <pre>
     * 将List<OrderMaster>对象转换为List<OrderDTO>对象
     * @param orderMasterList
     * @return List<OrderDTO>对象
     * </pre>
     */
    public static List<OrderDTO> convertToOrderDTOList(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(master -> convert(master)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<OrderMaster>对象转换为Page<OrderDTO>对象
     * @param orderMasterPage
     * @return Page<OrderDTO>对象
     * </pre>
     */
    public static Page<OrderDTO> convertToOrderDTOPage(Page<OrderMaster> orderMasterPage, Pageable pageable) {
        return new PageImpl<>(convertToOrderDTOList(orderMasterPage.getContent()), pageable, orderMasterPage.getTotalElements());
    }

    /**
     * <pre>
     * 将orderDTO对象转换为oderMaster对象
     * @param orderDTO
     * @return OrderDTO对象
     * </pre>
     */
    public static OrderMaster convert(OrderDTO orderDTO) {
        OrderMaster orderMaster = OrderMaster.builder().build();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        return orderMaster;
    }

    /**
     * <pre>
     * 将List<OrderDTO>对象转换为List<OrderMaster>对象
     * @param orderDTOList
     * @return List<OrderDTO>
     * </pre>
     */
    public static List<OrderMaster> convertToOrderMasterList(List<OrderDTO> orderDTOList) {
        return orderDTOList.stream().map(orderDTO -> convert(orderDTO)).collect(Collectors.toList());
    }

    /**
     * <pre>
     * 将Page<OrderDTO>转换为Page<OrderMaster>对象
     * @param orderDTOPage
     * @param pageable
     * @return Page<OrderMaster>对象
     * </pre>
     */
    public static Page<OrderMaster> convertToOrderMasterPage(Page<OrderDTO> orderDTOPage, Pageable pageable) {
        return new PageImpl<>(convertToOrderMasterList(orderDTOPage.getContent()), pageable, orderDTOPage.getTotalElements());
    }
}
