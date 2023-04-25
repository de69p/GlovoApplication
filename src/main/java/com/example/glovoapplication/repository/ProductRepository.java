package com.example.glovoapplication.repository;

import com.example.glovoapplication.mapper.ProductRowMapper;
import com.example.glovoapplication.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product getProductById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return (Product) jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductRowMapper());
    }

    public void createProduct(Product product) {
        String sql = "INSERT INTO products (name, cost) VALUES (?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getCost());
    }


    public void updateProduct(Long id, Product product) {
        String sql = "UPDATE products SET name = ?, cost = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getCost(), id);
    }

    public void deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}

