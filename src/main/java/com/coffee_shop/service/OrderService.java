package com.coffee_shop.service;

import com.coffee_shop.dto.OrderDto;
import com.coffee_shop.entity.Order;

public interface OrderService {

   public Order createOrder(OrderDto orderDto);

    public Order getOrderById(Long id);

    public Order updateOrderById(Long id, OrderDto orderDto);

    public void deleteOrderById(Long id);
}