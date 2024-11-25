package com.demo.bookstore;

import com.demo.bookstore.common.enums.OrderStatus;
import com.demo.bookstore.dto.OrderDTO;
import com.demo.bookstore.dto.OrderStatusDTO;
import com.demo.bookstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTests extends BaseTest{

    @Autowired
    private OrderService orderService;

    @Test
    public void testCreate(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAmount(new BigDecimal(200.00));

        OrderDTO.Item item1 = new OrderDTO.Item();
        item1.setQuantity(8);
        item1.setBookId(1L);

        OrderDTO.Item item2 = new OrderDTO.Item();
        item2.setQuantity(8);
        item2.setBookId(2L);

        Set<OrderDTO.Item> items = new HashSet<>();
        items.add(item1);
        items.add(item2);
        orderDTO.setItems(items);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<OrderDTO> entity = new HttpEntity<>(orderDTO, headers);
        ResponseEntity<OrderDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/order",
                HttpMethod.POST,
                entity,
                OrderDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(OrderStatus.PENDING_PAYMENT);
    }

    @Test
    public void testCancel() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<OrderStatusDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/order/cancel/1",
                HttpMethod.PUT,
                entity,
                OrderStatusDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @Test
    public void testCheck() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<OrderStatusDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/order/status/1",
                HttpMethod.GET,
                entity,
                OrderStatusDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(OrderStatus.CANCEL);
    }
}
