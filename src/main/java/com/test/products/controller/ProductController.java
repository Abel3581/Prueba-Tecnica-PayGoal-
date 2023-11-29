package com.test.products.controller;

import com.test.products.model.request.ProductRequest;
import com.test.products.model.request.ProductUpdateRequest;
import com.test.products.model.response.ProductResponse;
import com.test.products.model.response.ProductUpdateResponse;
import com.test.products.service.injectiondependency.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ProductRequest request){
        log.info("Entrando al controlador create" + request.toString());
        productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        ProductResponse response = productService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleted(@PathVariable Long id){
        productService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).body("Producto borrado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request){
        log.info("Entrando al Controller update");
        ProductUpdateResponse response = productService.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Consultar todos los productos ordenados por precio.

    @GetMapping("/order")
    public ResponseEntity<List<ProductResponse>> getAllProductsOrderByPrice(
            @RequestParam(defaultValue = "asc") String sortOrder)
    {
        List<ProductResponse> responses = productService.getAllProductsOrderByPrice(sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
