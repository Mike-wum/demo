package com.demo.bookstore.dao;

import com.demo.bookstore.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InventoryDao extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

    Inventory findByBookId(Long bookId);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update Inventory i set i.stock=i.stock-?2 where i.bookId=?1 and i.stock-?2>=0")
    int reduceStock(Long bookId, Integer quantity);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update Inventory i set i.stock=i.stock+?2 where i.bookId=?1")
    int increaseStock(Long bookId, Integer quantity);
}
