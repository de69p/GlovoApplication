package com.example.glovoapplication.repository;

import com.example.glovoapplication.mapper.OrderRowMapper;
import com.example.glovoapplication.model.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        return jdbcTemplate.query(sql, new OrderRowMapper());
    }

    public Order getOrderById(Long id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new OrderRowMapper());
    }

    public void createOrder(Order order) {
        String sql = "INSERT INTO orders (date, cost) VALUES (?, ?)";
        jdbcTemplate.update(sql, order.getDate(), order.getCost());
    }


    public void updateOrder(Long id, Order order) {
        String sql = "UPDATE orders SET date = ?, cost = ? WHERE id = ?";
        jdbcTemplate.update(sql, order.getDate(), order.getCost(), id);
    }
    public void deleteOrder(Long id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}



