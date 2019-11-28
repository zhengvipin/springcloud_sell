package com.catt.product.service;

import com.catt.product.dataobject.ProductInfo;
import com.catt.product.dto.CartDTO;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     */
    List<ProductInfo> findByProductIdIn(List<String> productIdList);

    /**
     * 扣库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
