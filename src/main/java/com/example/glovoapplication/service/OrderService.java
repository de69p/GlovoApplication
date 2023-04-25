package com.example.glovoapplication.service;

import com.example.glovoapplication.model.Order;
import com.example.glovoapplication.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(Long id) {
        return orderRepository.getOrderById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public void createOrder(Order order) {
        orderRepository.createOrder(order);
    }

    public void updateOrder(Long id, Order order) {
        orderRepository.updateOrder(id, order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteOrder(id);
    }
}
