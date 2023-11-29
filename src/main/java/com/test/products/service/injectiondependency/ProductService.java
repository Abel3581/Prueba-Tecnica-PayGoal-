package com.test.products.service.injectiondependency;

import com.test.products.model.request.ProductRequest;
import com.test.products.model.request.ProductUpdateRequest;
import com.test.products.model.response.ProductResponse;
import com.test.products.model.response.ProductUpdateResponse;

import java.util.List;

public interface ProductService {

    void create(ProductRequest request);

    ProductResponse getById(Long id);

    ProductUpdateResponse update(ProductUpdateRequest request, Long id);

    void deleted(Long id);

    List<ProductResponse> getAllProductsOrderByPrice(String sortOrder);
}
