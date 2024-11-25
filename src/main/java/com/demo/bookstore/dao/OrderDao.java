package com.demo.bookstore.dao;

import com.demo.bookstore.common.enums.OrderStatus;
import com.demo.bookstore.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderDao extends JpaRepository<OrderDetail, Long>, JpaSpecificationExecutor<OrderDetail> {
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update OrderDetail o set o.status=?2 where o.id=?1")
    int updateStatus(Long id, OrderStatus orderStatus);
}
