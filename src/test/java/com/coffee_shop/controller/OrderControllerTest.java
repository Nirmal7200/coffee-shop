package com.coffee_shop.controller;

import com.coffee_shop.dto.OrderDto;
import com.coffee_shop.entity.Order;
import com.coffee_shop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private OrderDto orderDto;
    private Order order;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();

        // Shared test data
        orderDto = new OrderDto();
        orderDto.setName("Espresso");
        orderDto.setQuantity(2);
        orderDto.setPrice(100);

        order = new Order();
        order.setId(1L);
        order.setName("Espresso");
        order.setQuantity(2);
        order.setPrice(100);
        order.setTotal(200.0);
    }

    // ─────────────────────────────────────────────
    // POST /order
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("POST /order: should create order and return 201 CREATED")
    void createOrder_shouldReturn201WithCreatedOrder() throws Exception {
        when(orderService.createOrder(any(OrderDto.class))).thenReturn(order);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Espresso"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.total").value(200.0));

        verify(orderService, times(1)).createOrder(any(OrderDto.class));
    }

    // ─────────────────────────────────────────────
    // GET /order/{id}
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("GET /order/{id}: should return order with 200 OK")
    void getOrderById_shouldReturn200WithOrder() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/order/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Espresso"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.total").value(200.0));

        verify(orderService, times(1)).getOrderById(1L);
    }

    // ─────────────────────────────────────────────
    // PUT /order/{id}
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("PUT /order/{id}: should update order and return 202 ACCEPTED")
    void updateOrder_shouldReturn202WithUpdatedOrder() throws Exception {
        Order updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setName("Latte");
        updatedOrder.setQuantity(3);
        updatedOrder.setPrice(150);
        updatedOrder.setTotal(450.0);

        OrderDto updateDto = new OrderDto();
        updateDto.setName("Latte");
        updateDto.setQuantity(3);
        updateDto.setPrice(150);

        when(orderService.updateOrderById(eq(1L), any(OrderDto.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/order/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Latte"))
                .andExpect(jsonPath("$.quantity").value(3))
                .andExpect(jsonPath("$.price").value(150))
                .andExpect(jsonPath("$.total").value(450.0));

        verify(orderService, times(1)).updateOrderById(eq(1L), any(OrderDto.class));
    }

    // ─────────────────────────────────────────────
    // DELETE /order/{id}
    // ─────────────────────────────────────────────

    @Test
    @DisplayName("DELETE /order/{id}: should delete order and return 200 with message")
    void deleteOrder_shouldReturn200WithMessage() throws Exception {
        doNothing().when(orderService).deleteOrderById(1L);

        mockMvc.perform(delete("/order/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Order with ID 1 deleted successfully"));

        verify(orderService, times(1)).deleteOrderById(1L);
    }
}