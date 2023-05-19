package com.example.glovoapplication.controller;

import com.example.glovoapplication.model.Order;
import com.example.glovoapplication.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void testGetOrderById() throws Exception {
        Long orderId = 1L;
        Order order = new Order(orderId, LocalDate.now(), 100.0);
        when(orderService.getOrderById(orderId)).thenReturn(order);

        mockMvc.perform(get("/orders/" + orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(orderId.intValue())))
                .andExpect(jsonPath("$.date", Matchers.is(order.getDate().toString())))
                .andExpect(jsonPath("$.cost", Matchers.is(order.getCost())))
                .andExpect(jsonPath("$.products", Matchers.nullValue()));
    }


    @Test
    public void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(
                new Order(1L, LocalDate.now(), 100.0),
                new Order(2L, LocalDate.now(), 200.0)
        );
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(orders.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].date", Matchers.is(orders.get(0).getDate().toString())))
                .andExpect(jsonPath("$[0].cost", Matchers.is(orders.get(0).getCost())))
                .andExpect(jsonPath("$[0].products", Matchers.nullValue()))
                .andExpect(jsonPath("$[1].id", Matchers.is(orders.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].date", Matchers.is(orders.get(1).getDate().toString())))
                .andExpect(jsonPath("$[1].cost", Matchers.is(orders.get(1).getCost())))
                .andExpect(jsonPath("$[1].products", Matchers.nullValue()));
    }


    @Test
    public void testCreateOrder() throws Exception {
        Order order = new Order(1L, 100.0);
        String orderJson = new ObjectMapper().writeValueAsString(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk());

        verify(orderService, times(1)).createOrder(order);
    }


    @Test
    public void testUpdateOrder() throws Exception {
        Long orderId = 1L;
        Order order = new Order(orderId, 99.0);
        String orderJson = new ObjectMapper().writeValueAsString(order);

        mockMvc.perform(put("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk());

        verify(orderService, times(1)).updateOrder(orderId, order);
    }

    @Test
    void testDeleteOrder() throws Exception {
        Long orderId = 1L;

        mockMvc.perform(delete("/orders/{id}", orderId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrder(orderId);
    }
}

