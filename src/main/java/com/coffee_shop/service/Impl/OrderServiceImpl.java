package com.coffee_shop.service.Impl;

import com.coffee_shop.dto.OrderDto;
import com.coffee_shop.entity.Order;
import com.coffee_shop.repository.OrderRepository;
import com.coffee_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setName(orderDto.getName());
        order.setQuantity(orderDto.getQuantity());
        order.setPrice(orderDto.getPrice());
        order.setTotal(orderDto.getQuantity() * orderDto.getPrice());

      return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {

        return orderRepository.getById(id);
    }

    @Override
    public Order updateOrderById(Long id, OrderDto orderDto) {

        Order updateOrder = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found with id"+ id));

        updateOrder.setName(orderDto.getName());
        updateOrder.setQuantity(orderDto.getQuantity());
        updateOrder.setPrice(orderDto.getPrice());
        updateOrder.setTotal(orderDto.getPrice() * orderDto.getQuantity());

        return orderRepository.save(updateOrder);

    }

    @Override
    public void deleteOrderById(Long id) {
      Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found with Id "+ id));
      orderRepository.delete(order);
    }
}
