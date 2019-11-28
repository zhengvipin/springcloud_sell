package com.catt.order.service;

import com.catt.order.dto.OrderDTO;

public interface OrderService {
    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDTO);
}
