package com.test.products.util;


import com.test.products.model.entity.ProductEntity;
import com.test.products.model.request.ProductRequest;
import com.test.products.model.request.ProductUpdateRequest;

import java.util.List;

public class ProductUtil {

    public static ProductRequest createProductTest(){
        return ProductRequest.builder()
                .name("Pepsi")
                .description("Bebida")
                .price(2699)
                .count(10)
                .build();
    }

    public static List<ProductEntity> createProductListTest() {
        return List.of(
                new ProductEntity("Java", "description", 100,  10),
                new ProductEntity("Spring Boot","description", 20.99, 100)
        );
    }

    public static ProductEntity createProductEntityTest(){
        return ProductEntity.builder()
                .name("Python")
                .price(9000)
                .count(39)
                .description("12")
                .build();
    }
    public static ProductUpdateRequest createProductUpdateTest(){
        return ProductUpdateRequest.builder()
                .name("JavaScript")
                .price(7000)
                .count(30)
                .description("29")
                .build();
    }


    public static ProductUpdateRequest createProductUpdateFailureTest() {
        return ProductUpdateRequest.builder()
                .name("Angular")
                .price(7000)
                .count(30)
                .description("29")
                .build();

    }

    public static ProductEntity createProducUpdatetEntityTest() {
        return ProductEntity.builder()
                .name("PHP")
                .price(9000)
                .count(39)
                .description("12")
                .build();

    }
}
