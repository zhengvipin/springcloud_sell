package com.catt.product.service;

import com.catt.product.dataobject.ProductInfo;
import com.catt.product.dto.CartDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() {
        List<ProductInfo> list = productService.findUpAll();
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void findByProductIdIn() {
        List<ProductInfo> list = productService.findByProductIdIn(Arrays.asList("157875196366160022", "157875227953464068"));
        Assertions.assertTrue(list.size() > 0);
    }

    @Test
    public void decreaseStock() {
        CartDTO cartDTO = new CartDTO("157875196366160022", 2);
        productService.decreaseStock(Collections.singletonList(cartDTO));
    }
}