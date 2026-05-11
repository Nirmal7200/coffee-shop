package com.coffee_shop.service;

import com.coffee_shop.dto.OrderDto;
import com.coffee_shop.entity.Order;
import com.coffee_shop.repository.OrderRepository;
import com.coffee_shop.service.Impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDto orderDto;
    private Order savedOrder;

    @BeforeEach
    void setUp(){
        orderDto = new OrderDto();

        orderDto.setName("Cappuccino");
        orderDto.setQuantity(2);
        orderDto.setPrice(150);

        savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setName("Cappuccino");
        savedOrder.setQuantity(2);
        savedOrder.setPrice(150);
        savedOrder.setTotal(300.0);
    }

//Create Order
    @Test
    @DisplayName("createOrder: should map DTOm compute total, and persist")
    void createOrder_shouldSaveAndReturnOrder(){
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Order result = orderService.createOrder(orderDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Cappuccino");
        assertThat(result.getQuantity()).isEqualTo(2);
        assertThat(result.getPrice()).isEqualTo(150);
        assertThat(result.getTotal()).isEqualTo(300.0);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("createOrder: total should equal quantity * price")
    void createOrder_shouldComputeTotalCorrectly(){
        orderDto.setQuantity(3);
        orderDto.setPrice(200);

        Order expectedOrder = new Order();
        expectedOrder.setId(2L);
        expectedOrder.setName("Cappuccino");
        expectedOrder.setQuantity(3);
        expectedOrder.setPrice(200);
        expectedOrder.setTotal(600.0);

        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);

        Order result = orderService.createOrder(orderDto);

        assertThat(result.getTotal()).isEqualTo(600.0);
        verify(orderRepository).save(any(Order.class));
    }

    //Get order by Id
    @Test
    void getOrderById(){
        when(orderRepository.getById(1L)).thenReturn(savedOrder);

        Order result = orderService.getOrderById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Cappuccino");

        verify(orderRepository, times(1)).getById(1L);
    }

    //Update OrderById
    @Test
    void updateOrderById(){
        OrderDto updateDto = new OrderDto();
        updateDto.setName("Latte");
        updateDto.setQuantity(3);
        updateDto.setPrice(200);

        Order updatedOrder = new Order(1L, "Latte",3,200,600.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(savedOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrderById(1L, updateDto);

        assertThat(result.getName()).isEqualTo("Latte");
        assertThat(result.getQuantity()).isEqualTo(3);
        assertThat(result.getPrice()).isEqualTo(200);
        assertThat(result.getTotal()).isEqualTo(600.0);

        verify(orderRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));

    }

    @Test
    void updateOrderById_ThrowNotFound(){
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> orderService.updateOrderById(99L, orderDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Order not found with id");

        verify(orderRepository).findById(99L);
        verify(orderRepository,never()).save(any());
    }

    //deleteOrderById

    @Test
    void deleteOrderById_ShouldDeleteOrder(){
        when(orderRepository.findById(1L)).thenReturn(Optional.of(savedOrder));
        doNothing().when(orderRepository).delete(savedOrder);

        orderService.deleteOrderById(1L);

        verify(orderRepository).findById(1L);
        verify(orderRepository).delete(savedOrder);
    }

    @Test
    void deleteOrderById_shouldThrowWhenNotFound(){
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> orderService.deleteOrderById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Order not found with Id");

        verify(orderRepository).findById(99L);
        verify(orderRepository,never()).delete(any());
    }

}
