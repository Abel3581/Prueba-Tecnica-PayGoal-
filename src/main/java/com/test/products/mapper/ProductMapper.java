package com.test.products.mapper;

import com.test.products.model.entity.ProductEntity;
import com.test.products.model.request.ProductRequest;
import com.test.products.model.response.ProductResponse;
import com.test.products.model.response.ProductUpdateResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductEntity mapToProductRequest(ProductRequest request) {
        return ProductEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .count(request.getCount())
                .price(request.getPrice())
                .build();
    }

    public ProductResponse mapToProductResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .count(productEntity.getCount())
                .price(productEntity.getPrice())
                .build();
    }

    public ProductUpdateResponse mapToProductUpdate(ProductEntity productEntity) {
        return ProductUpdateResponse.builder()
                .name(productEntity.getName())
                .count(productEntity.getCount())
                .price(productEntity.getPrice())
                .description(productEntity.getDescription())
                .message("Producto actualizado")
                .build();
    }

    public List<ProductResponse> convertToProductResponses(List<ProductEntity> productEntities) {
        return productEntities.stream().map(productEntity -> mapToProductResponse(productEntity))
                .collect(Collectors.toList());
    }
}
