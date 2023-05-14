package com.example.glovoapplication.service;

import com.example.glovoapplication.exception.OrderNotFoundException;
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
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(id)
                        .orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
        existingOrder.setDate(updatedOrder.getDate());
        existingOrder.setCost(updatedOrder.getCost());
        orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
