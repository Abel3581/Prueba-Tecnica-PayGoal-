package com.test.products.controller;

import com.test.products.model.request.ProductRequest;
import com.test.products.model.request.ProductUpdateRequest;
import com.test.products.model.response.ProductResponse;
import com.test.products.model.response.ProductUpdateResponse;
import com.test.products.service.injectiondependency.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Crea un producto", description = "Crea un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado con exito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "409", description = "El producto ya esta registrado",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ProductRequest request){
        log.info("Entrando al controlador create" + request.toString());
        productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito");
    }

    @Operation(summary = "Buscar producto por id", description = "Busca un producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))}),
            @ApiResponse(responseCode = "404", description = "El producto no esta registrado",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        ProductResponse response = productService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Borrar producto", description = "Borra producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "El producto no esta registrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleted(@PathVariable Long id){
        productService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).body("Producto borrado con exito");
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductUpdateResponse.class))}),
            @ApiResponse(responseCode = "404", description = "El producto no esta registrado",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request){
        log.info("Entrando al Controller update");
        ProductUpdateResponse response = productService.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Consultar todos los productos ordenados por precio.
    //"Invalid sortOrder value: "
    @Operation(summary = "Devuelve la lista de productos por orden asc y desc", description = "Devuelve la lista de productos por orden de precio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Invalid sortOrder value: + sortOrder",
                    content = @Content)})
    @GetMapping("/order")
    public ResponseEntity<List<ProductResponse>> getAllProductsOrderByPrice(
            @RequestParam(defaultValue = "asc") String sortOrder)
    {
        List<ProductResponse> responses = productService.getAllProductsOrderByPrice(sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
