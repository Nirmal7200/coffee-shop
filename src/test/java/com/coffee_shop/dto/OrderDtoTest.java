package com.coffee_shop.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDtoTest {

    @Test
    @DisplayName("OrderDto: setters and getters should work correctly")
    void orderDto_settersAndGetters() {
        OrderDto dto = new OrderDto();
        dto.setName("Cold Brew");
        dto.setQuantity(4);
        dto.setPrice(180);

        assertThat(dto.getName()).isEqualTo("Cold Brew");
        assertThat(dto.getQuantity()).isEqualTo(4);
        assertThat(dto.getPrice()).isEqualTo(180);
    }

    @Test
    @DisplayName("OrderDto: equals and hashCode should match for identical values")
    void orderDto_equalsAndHashCode() {
        OrderDto dto1 = new OrderDto();
        dto1.setName("Cold Brew");
        dto1.setQuantity(4);
        dto1.setPrice(180);

        OrderDto dto2 = new OrderDto();
        dto2.setName("Cold Brew");
        dto2.setQuantity(4);
        dto2.setPrice(180);

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    @DisplayName("OrderDto: toString should contain all field values")
    void orderDto_toStringShouldContainFields() {
        OrderDto dto = new OrderDto();
        dto.setName("Cold Brew");
        dto.setQuantity(4);
        dto.setPrice(180);

        String toString = dto.toString();

        assertThat(toString).contains("Cold Brew");
        assertThat(toString).contains("180");
    }
}
