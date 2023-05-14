package com.example.glovoapplication.service;

import com.example.glovoapplication.model.Order;
import com.example.glovoapplication.model.Product;
import com.example.glovoapplication.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void testGetOrderById() {
        Long id = 1L;
        Order order = new Order();
        order.setId(id);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Order result = orderService.getOrderById(id);
        assertEquals(order, result);
    }

    @Test
    public void testGetAllOrders() {
        List<Order> orderList = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(orderList);
        List<Order> result = orderService.getAllOrders();
        assertEquals(orderList, result);
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);
        orderService.createOrder(order);
        verify(orderRepository).save(order);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void testUpdateOrder() {
        Long orderId = 1L;
        Order existingOrder = new Order(orderId, LocalDate.now(), 50.00, List.of(new Product(), new Product()));
        Order updateOrder = new Order(orderId, LocalDate.now(), 75.00, List.of(new Product(), new Product()));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        orderService.updateOrder(orderId, updateOrder);
        verify(orderRepository, times(1)).save(updateOrder);
    }

    @Test
    public void testDeleteOrder() {
        Long id = 1L;
        doNothing().when(orderRepository).deleteById(id);
        orderService.deleteOrder(id);
        verify(orderRepository).deleteById(id);
    }

}
