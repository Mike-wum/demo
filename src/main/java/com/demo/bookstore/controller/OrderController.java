package com.demo.bookstore.controller;

import com.demo.bookstore.dto.OrderDTO;
import com.demo.bookstore.dto.OrderStatusDTO;
import com.demo.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        OrderDTO dto = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/cancel/{id}")
    public OrderStatusDTO cancel(@PathVariable("id") Long id) {
        return orderService.cancelOrder(id);
    }

    @GetMapping(value = "/status/{id}")
    public OrderStatusDTO checkStatus(@PathVariable("id") Long id) {
        return orderService.checkStatus(id);
    }
}
