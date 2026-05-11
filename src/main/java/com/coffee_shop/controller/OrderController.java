package com.coffee_shop.controller;

import com.coffee_shop.dto.OrderDto;
import com.coffee_shop.entity.Order;
import com.coffee_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody  OrderDto orderDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.updateOrderById(id, orderDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id){
        orderService.deleteOrderById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Order with ID "+ id + " deleted successfully");
    }


}