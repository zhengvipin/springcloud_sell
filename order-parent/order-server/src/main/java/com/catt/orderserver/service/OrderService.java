package com.catt.orderserver.service;

import com.catt.orderserver.dto.OrderDTO;

public interface OrderService {
    /**
     * 创建订单
     */
    OrderDTO create(OrderDTO orderDTO);
}
