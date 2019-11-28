package com.catt.product.repository;

import com.catt.product.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository通过注解方式由spring容器管理
 * ProductInfoRepository继承了JpaRepository，所以ProductInfoRepository也默认由spring容器管理
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    List<ProductInfo> findByProductStatus(Integer productStatus);

    List<ProductInfo> findByProductIdIn(List<String> productIdList);
}
