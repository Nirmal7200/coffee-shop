package com.coffee_shop.entity;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    @DisplayName("Order: no-args constructor and setters should work correctly")
    void order_noArgsConstructorAndSetters() {
        Order order = new Order();
        order.setId(1L);
        order.setName("Mocha");
        order.setQuantity(3);
        order.setPrice(120);
        order.setTotal(360.0);

        assertThat(order.getId()).isEqualTo(1L);
        assertThat(order.getName()).isEqualTo("Mocha");
        assertThat(order.getQuantity()).isEqualTo(3);
        assertThat(order.getPrice()).isEqualTo(120);
        assertThat(order.getTotal()).isEqualTo(360.0);
    }

    @Test
    @DisplayName("Order: all-args constructor should set all fields")
    void order_allArgsConstructor() {
        Order order = new Order(5L, "Americano", 1, 80, 80.0);

        assertThat(order.getId()).isEqualTo(5L);
        assertThat(order.getName()).isEqualTo("Americano");
        assertThat(order.getQuantity()).isEqualTo(1);
        assertThat(order.getPrice()).isEqualTo(80);
        assertThat(order.getTotal()).isEqualTo(80.0);
    }

    @Test
    @DisplayName("Order: equals and hashCode should be based on field values")
    void order_equalsAndHashCode() {
        Order order1 = new Order(1L, "Cappuccino", 2, 150, 300.0);
        Order order2 = new Order(1L, "Cappuccino", 2, 150, 300.0);

        assertThat(order1).isEqualTo(order2);
        assertThat(order1.hashCode()).isEqualTo(order2.hashCode());
    }

    @Test
    @DisplayName("Order: toString should contain field values")
    void order_toStringShouldContainFields() {
        Order order = new Order(1L, "Flat White", 2, 130, 260.0);

        String toString = order.toString();

        assertThat(toString).contains("Flat White");
        assertThat(toString).contains("260.0");
    }
}
