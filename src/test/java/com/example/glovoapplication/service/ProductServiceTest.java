package com.example.glovoapplication.service;

import com.example.glovoapplication.model.Product;
import com.example.glovoapplication.repository.ProductRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetProductById() {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Product result = productService.getProductById(id);
        assertEquals(product, result);

    }

    @Test
    public void testGetAllProducts() {
        List<Product> productList = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> result = productService.getAllProducts();
        assertEquals(productList, result);
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);
        productService.createProduct(product);
        verify(productRepository).save(product);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testUpdateProduct() {
        Long productId = 1L;
        Product existingProduct = new Product(productId, "existing product", 10.0);
        Product updatedProduct = new Product(productId, "updated product", 20.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        productService.updateProduct(productId, updatedProduct);
        verify(productRepository, times(1)).save(updatedProduct);
    }


    @Test
    public void TestDeleteProduct() {
        Long id = 1L;
        doNothing().when(productRepository).deleteById(id);
        productService.deleteProduct(id);
        verify(productRepository).deleteById(id);
    }
}

