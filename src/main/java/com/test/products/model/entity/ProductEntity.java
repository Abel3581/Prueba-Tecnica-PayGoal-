package com.test.products.model.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int count;

    public ProductEntity(String name, String description, double price, int count) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;

    }

    public ProductEntity(Long id, String name, String description, double price, int count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.count = count;
    }
}
