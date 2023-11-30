package com.test.products.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.products.model.entity.ProductEntity;
import com.test.products.model.request.ProductRequest;
import com.test.products.model.request.ProductUpdateRequest;
import com.test.products.model.response.ProductResponse;
import com.test.products.model.response.ProductUpdateResponse;
import com.test.products.repository.ProductRepository;
import com.test.products.service.injectiondependency.ProductService;
import com.test.products.util.ProductUtil;
import jakarta.transaction.Transactional;
import org.junit.Before;
// Importaciones nuevas
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class ProductEntityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        // Cargar datos iniciales en la base de datos de prueba
        List<ProductEntity> productEntity = ProductUtil.createProductListTest();
        productRepository.saveAll(productEntity);
    }


    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testCreateProductWithAdminRole() throws Exception {
        // Preparar una solicitud POST con el cuerpo adecuado
        ProductRequest request = ProductUtil.createProductTest();
        String content = objectMapper.writeValueAsString(request);

        // Realizar la solicitud POST al controlador
        mockMvc.perform(post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Producto creado con exito"));
    }


    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testCreateProductDuplicate() throws Exception {

        ProductRequest request = ProductUtil.createProductTest();

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    String content2 = result.getResponse().getContentAsString();
                    assertThat(content2).contains("El producto ya esta registrado");
                });
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testGetProductById() throws Exception {
        Long productId = 1L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ProductResponse productResponse = objectMapper.readValue(responseJson, ProductResponse.class);

        assertEquals(productId, productResponse.getId());
        assertEquals("Java", productResponse.getName());
        assertEquals(100, productResponse.getPrice(), 0.01);
        assertEquals("description", productResponse.getDescription());
        assertEquals(10, productResponse.getCount());



    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testGetProductByIdFailure() throws Exception {
        Long productId = 7L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId))
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("El producto no esta registrado");
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testUpdateProduct() throws Exception {
        ProductEntity productEntity = ProductUtil.createProductEntityTest();
        productRepository.save(productEntity);

        ProductUpdateRequest request = ProductUtil.createProductUpdateTest();

        String updateJson = objectMapper.writeValueAsString(request);

        Long productId = productEntity.getId();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/products/" + productId)
                .content(updateJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Obtener la respuesta JSON como una cadena
        String responseContent = result.getResponse().getContentAsString();
        // Analizar la respuesta JSON a un objeto ProductUpdateResponse
        ProductUpdateResponse updateResponse = objectMapper.readValue(responseContent, ProductUpdateResponse.class);
        // Realizar verificaciones en el objeto ProductUpdateResponse
        assertEquals("JavaScript", updateResponse.getName());
        assertEquals(7000, updateResponse.getPrice(), 0.01);
        assertEquals("29", updateResponse.getDescription());
        assertEquals(30, updateResponse.getCount());
        assertEquals("Producto actualizado", updateResponse.getMessage());
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testUpdateProductFailure() throws Exception {
        ProductEntity productEntity = ProductUtil.createProducUpdatetEntityTest();
        productRepository.save(productEntity);

        ProductUpdateRequest request = ProductUtil.createProductUpdateFailureTest();

        String updateJson = objectMapper.writeValueAsString(request);

        Long productId = 1000L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/products/" + productId)
                        .content(updateJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("El producto no esta registrado");
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testDeletedProduct() throws Exception {
        ProductEntity productEntity = ProductUtil.createProductEntityTest();
        productRepository.save(productEntity);

        Long productId = productEntity.getId();
        mockMvc.perform(delete("/products/" + productId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertFalse(productRepository.findById(productId).isPresent());
    }

    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testDeleteProductNotFound() throws Exception {
        // Intentar eliminar un producto que no existe

        Long productId = 20L;
        MvcResult result = mockMvc.perform(delete("/products/" + productId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andReturn();

        assertFalse(productRepository.findById(productId).isPresent());
        // Obtener el mensaje de la excepción
        String errorMessage = result.getResponse().getContentAsString();
        // Realizar verificaciones en el mensaje de la excepción
        assertThat(errorMessage).contains("El producto no esta registrado");

    }
}
