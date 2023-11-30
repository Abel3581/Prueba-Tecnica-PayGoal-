package com.test.products.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "La descripcion es requerida")
    private String description;
    @Min(value = 0, message = "El valor debe ser mayor o igual a 0")
    private double price;
    @Min(value = 0, message = "El valor debe ser mayor o igual a 0")
    @Max(value = 100, message = "El valor debe ser menor o igual a 100")
    private int count;
}
