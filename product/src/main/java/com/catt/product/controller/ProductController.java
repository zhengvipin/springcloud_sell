package com.catt.product.controller;

import com.catt.product.dataobject.ProductCategory;
import com.catt.product.dataobject.ProductInfo;
import com.catt.product.dto.CartDTO;
import com.catt.product.service.CategoryService;
import com.catt.product.service.ProductService;
import com.catt.product.utils.ResultVOUtil;
import com.catt.product.vo.ProductInfoVO;
import com.catt.product.vo.ProductVO;
import com.catt.product.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    /**
     * 1.查询所有在架的商品
     * 2.查询类目type列表
     * 3.查询类目
     * 4.构造数据
     * <p>
     * ResultVO - ProductVO - ProductInfoVO
     */
    @GetMapping("/list")
    public ResultVO<List> list() {
        return listImpl();
    }

    /**
     * 获取商品列表（给订单服务用的）
     *
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder") // @RequestBody配合@PostMapping使用
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList) {
        return productService.findByProductIdIn(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList) {
        productService.decreaseStock(cartDTOList);
    }

    private ResultVO<List> listImpl() {
        // 1.查询所有在架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 2.查询类目type列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        // 3.从数据库查询类目
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 4.构造数据
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(productCategory, productVO);

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }

//    private Map<String, Object> listImpl() {
//        // 1.查询所有在架的商品
//        List<ProductInfo> productInfoList = productService.findUpAll();
//
//        // 2.查询类目type列表
//        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
//
//        // 3.从数据库查询类目
//        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
//
//        // 4.构造数据
//        List<Map> data = new ArrayList<>();
//        for (ProductCategory productCategory : productCategoryList) {
//            Map<String, Object> categoryMap = new HashMap<>();
//            categoryMap.put("name", productCategory.getCategoryName());
//            categoryMap.put("type", productCategory.getCategoryType());
//
//            List<Map> foods = new ArrayList<>();
//            for (ProductInfo productInfo : productInfoList) {
//                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
//                    Map<String, Object> productMap = new HashMap<>();
//                    productMap.put("id", productInfo.getProductId());
//                    productMap.put("name", productInfo.getProductName());
//                    productMap.put("price", productInfo.getProductPrice());
//                    productMap.put("description", productInfo.getProductDescription());
//                    productMap.put("icon", productInfo.getProductIcon());
//                    foods.add(productMap);
//                }
//            }
//            categoryMap.put("foods", foods);
//            data.add(categoryMap);
//        }
//
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("code", 0);
//        resultMap.put("msg", "成功");
//        resultMap.put("data", data);
//
//        return resultMap;
//    }
}
