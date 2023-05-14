package com.example.glovoapplication.controller;

import com.example.glovoapplication.model.Product;
import com.example.glovoapplication.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetProductById() throws Exception {
        Long productId = 1L;
        Product product = new Product(productId, "Product 1", 10.0);
        when(productService.getProductById(productId)).thenReturn(product);

        mockMvc.perform(get("/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(productId.intValue())))
                .andExpect(jsonPath("$.name", Matchers.is(product.getName())))
                .andExpect(jsonPath("$.cost", Matchers.is(product.getCost())));
    }


    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", 10.0),
                new Product(2L, "Product 2", 20.0)
        );
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(products.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", Matchers.is(products.get(0).getName())))
                .andExpect(jsonPath("$[0].cost", Matchers.is(products.get(0).getCost())))
                .andExpect(jsonPath("$[1].id", Matchers.is(products.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", Matchers.is(products.get(1).getName())))
                .andExpect(jsonPath("$[1].cost", Matchers.is(products.get(1).getCost())));
    }


    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product(1L, "New Product", 100.0);
        String productJson = new ObjectMapper().writeValueAsString(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());

        verify(productService, times(1)).createProduct(product);
    }


    @Test
    public void testUpdateProduct() throws Exception {
        Long productId = 1L;
        Product product = new Product(productId, "Updated Product", 99.0);
        String productJson = new ObjectMapper().writeValueAsString(product);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk());

        verify(productService, times(1)).updateProduct(productId, product);
    }


    @Test
    void testDeleteProduct() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/products/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteProduct(id);
    }
}