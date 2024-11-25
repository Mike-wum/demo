package com.demo.bookstore.service;

import com.demo.bookstore.dto.OrderDTO;
import com.demo.bookstore.dto.OrderStatusDTO;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    OrderStatusDTO checkStatus(Long orderId);

    @Transactional
    OrderDTO createOrder(OrderDTO orderDTO);

    @Transactional
    OrderStatusDTO cancelOrder(Long orderId);
}
