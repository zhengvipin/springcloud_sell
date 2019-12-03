package com.catt.productserver.controller;

import com.catt.productcommon.DecreaseStockInput;
import com.catt.productcommon.ProductInfoOutput;
import com.catt.productserver.dataobject.ProductCategory;
import com.catt.productserver.dataobject.ProductInfo;
import com.catt.productserver.service.CategoryService;
import com.catt.productserver.service.ProductService;
import com.catt.productserver.utils.ResultVOUtil;
import com.catt.productserver.vo.ProductInfoVO;
import com.catt.productserver.vo.ProductVO;
import com.catt.productserver.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
     */
    @GetMapping("/list")
    public ResultVO<List> list() {
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

    /**
     * 获取商品列表（给订单服务用的）
     *
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder") // @RequestBody配合@PostMapping使用
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList) {
        return productService.findByProductIdIn(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputList) {
        productService.decreaseStock(decreaseStockInputList);
    }
}
