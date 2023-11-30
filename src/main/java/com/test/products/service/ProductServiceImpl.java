package com.test.products.service;

import com.test.products.exception.ProductAlreadyExistsException;
import com.test.products.exception.ProductNotFoundException;
import com.test.products.mapper.ProductMapper;
import com.test.products.model.entity.ProductEntity;
import com.test.products.model.request.ProductRequest;
import com.test.products.model.request.ProductUpdateRequest;
import com.test.products.model.response.ProductResponse;
import com.test.products.model.response.ProductUpdateResponse;
import com.test.products.repository.ProductRepository;
import com.test.products.service.injectiondependency.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Transactional
    @Override
    public void create(ProductRequest request) {
        log.info("Entrando al metodo crear producto en servicio");
        ProductEntity productEntity = productRepository.findByNameAndDescription(request.getName(),request.getDescription());
        if(productEntity == null ){
            ProductEntity productEntityCreate = productMapper.mapToProductRequest(request);
            productRepository.save(productEntityCreate);

        }else {
            log.error("Error al crear producto ");
            throw new ProductAlreadyExistsException("El producto ya esta registrado");
        }
    }

    @Override
    public ProductResponse getById(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        ProductResponse response = productMapper.mapToProductResponse(product.get());
        return response;
    }

    @Transactional
    @Override
    public ProductUpdateResponse update(ProductUpdateRequest request, Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        ProductEntity productEntity = productOptional.get();
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setPrice(request.getPrice());
        productEntity.setCount(request.getCount());
        ProductUpdateResponse response = productMapper.mapToProductUpdate(productEntity);
        return response;
    }

    @Override
    public void deleted(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        productRepository.delete(product.get());
    }

    @Override
    public List<ProductResponse> getAllProductsOrderByPrice(String sortOrder) {
        List<ProductEntity> productEntities;

        if ("asc".equalsIgnoreCase(sortOrder)) {
            productEntities = productRepository.findAllByOrderByPriceAsc();
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            productEntities = productRepository.findAllByOrderByPriceDesc();
        } else {
            // Handle invalid sortOrder value, or set a default behavior
            throw new IllegalArgumentException("Invalid sortOrder value: " + sortOrder);
        }

        // Convert ProductEntity list to ProductResponse list
        List<ProductResponse> productResponses = productMapper.convertToProductResponses(productEntities);

        return productResponses;
    }
}
