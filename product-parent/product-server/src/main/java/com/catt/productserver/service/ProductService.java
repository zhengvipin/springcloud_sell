package com.catt.productserver.service;

import com.catt.productcommon.DecreaseStockInput;
import com.catt.productcommon.ProductInfoOutput;
import com.catt.productserver.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     */
    List<ProductInfoOutput> findByProductIdIn(List<String> productIdList);

    /**
     * 扣库存
     */
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}
