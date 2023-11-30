package com.test.products.repository;

import com.test.products.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByNameAndDescription(String name, String description);

    List<ProductEntity> findAllByOrderByPriceAsc();

    List<ProductEntity> findAllByOrderByPriceDesc();
}
