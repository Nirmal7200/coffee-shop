package com.coffee_shop.dto;

import lombok.Data;

@Data
public class OrderDto {

    private String name;

    private long quantity;

    private long price;
}