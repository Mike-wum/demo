package com.demo.bookstore.service.impl;

import com.demo.bookstore.common.enums.OrderStatus;
import com.demo.bookstore.common.exception.BadRequestException;
import com.demo.bookstore.dao.BookDao;
import com.demo.bookstore.dao.OrderDao;
import com.demo.bookstore.dao.UserDao;
import com.demo.bookstore.dto.OrderDTO;
import com.demo.bookstore.dto.OrderStatusDTO;
import com.demo.bookstore.entity.Book;
import com.demo.bookstore.entity.OrderDetail;
import com.demo.bookstore.entity.OrderItem;
import com.demo.bookstore.entity.User;
import com.demo.bookstore.service.InventoryService;
import com.demo.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public OrderStatusDTO checkStatus(Long orderId) {
        Optional<OrderDetail> opt = orderDao.findById(orderId);
        if (opt.isPresent()) {
            return new OrderStatusDTO(opt.get().getStatus());
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // 先扣减库存，如果没有库存会抛出NoStockException,事务回滚
        for(OrderDTO.Item item : orderDTO.getItems()) {
            inventoryService.reduceStock(item.getBookId(), item.getQuantity());
        }

        // 扣减库存成功，创建订单，初始状态为待支付
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCreateTime(new Date());
        orderDetail.setStatus(OrderStatus.PENDING_PAYMENT);
        // 设置订单为当前用户
        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userDao.findByUsername(username);
        orderDetail.setUser(user);

        orderDetail.setAmount(orderDTO.getAmount());
        // 设置订单包含的书籍
        Set<OrderItem> orderItems = new HashSet<>();
        for(OrderDTO.Item item : orderDTO.getItems()) {
            OrderItem oi = new OrderItem();
            Book book = bookDao.getById(item.getBookId());
            oi.setBook(book);
            item.setPrice(book.getPrice());
            oi.setPrice(book.getPrice());
            oi.setQuantity(item.getQuantity());
            oi.setOrderDetail(orderDetail);
            orderItems.add(oi);
        }
        orderDetail.setOrderItems(orderItems);
        // 保存订单
        orderDao.save(orderDetail);
        // 返回订单相关信息
        orderDTO.setId(orderDetail.getId());
        orderDTO.setStatus(orderDetail.getStatus());
        orderDTO.setCreateTime(orderDetail.getCreateTime());
        orderDTO.setUserId(orderDetail.getUser().getId());
        return orderDTO;
    }

    @Transactional
    @Override
    public OrderStatusDTO cancelOrder(Long orderId) {
        Optional<OrderDetail> opt = orderDao.findById(orderId);
        if(opt.isPresent()) {
            OrderDetail detail = opt.get();
            if(detail.getStatus() != OrderStatus.PENDING_PAYMENT) {
                throw new BadRequestException("Order status error! Cannot Cancel!");
            }
            //释放库存
            for(OrderItem item : detail.getOrderItems()) {
                inventoryService.increaseStock(item.getBook().getId(), item.getQuantity());
            }

            orderDao.updateStatus(orderId, OrderStatus.CANCEL);

            return new OrderStatusDTO(OrderStatus.CANCEL);
        } else {
            throw new BadRequestException("Order not found!");
        }
    }
}
