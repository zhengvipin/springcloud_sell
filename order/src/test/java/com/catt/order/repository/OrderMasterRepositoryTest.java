package com.catt.order.repository;

import com.catt.order.dataobject.OrderMaster;
import com.catt.order.enums.OrderStatusEnum;
import com.catt.order.enums.PayStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("10010");
        orderMaster.setBuyerName("黄小明");
        orderMaster.setBuyerPhone("18700008888");
        orderMaster.setBuyerAddress("北京");
        orderMaster.setBuyerOpenid("hxm888");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assertions.assertNotNull(result);
    }
}