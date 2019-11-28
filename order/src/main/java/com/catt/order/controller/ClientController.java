package com.catt.order.controller;

import com.catt.order.client.ProductClient;
import com.catt.order.dataobject.ProductInfo;
import com.catt.order.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

//    @Autowired
//    private RestTemplate restTemplate;

    @GetMapping("/getProductMsgByRestTemplate")
    public String getProductMsgByRestTemplate() {
        // 1.第一种方式（直接使用restTemplate，url写死）
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:9871/msg", String.class);

        // 2.第二种方式（利用loadBalancerClient，通过应用名获取url，然后再使用restTemplate）
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/msg";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        // 3.第三种方式(利用@LoadBalanced，可在restTemplate里使用应用名)
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        log.info("response={}", response);
        return response;
    }

    // 声明式REST客户端（伪RPC）
    // 采用了基于接口的注解
    @GetMapping("/getProductMsgByFeign")
    public String getProductMsgByFeign() {
        String response = productClient.productMsg();
        log.info("response={}", response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<ProductInfo> productInfoList = productClient.listForOrder(Collections.singletonList("157875196366160022"));
        log.info("response={}", productInfoList);
        return "ok";
    }

    @GetMapping("/decreaseStock")
    public String decreaseStock() {
        CartDTO cartDTO = new CartDTO("157875196366160022", 3);
        productClient.decreaseStock(Collections.singletonList(cartDTO));
        return "ok";
    }
}
