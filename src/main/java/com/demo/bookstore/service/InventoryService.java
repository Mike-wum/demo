package com.demo.bookstore.service;

import com.demo.bookstore.common.exception.NoStockException;
import com.demo.bookstore.dto.InventoryDTO;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryService {
    boolean checkStock(Long bookId, Integer quantity);

    @Transactional
    void reduceStock(Long bookId, Integer quantity) throws NoStockException;

    @Transactional
    void increaseStock(Long bookId, Integer quantity);

    InventoryDTO create(InventoryDTO inventoryDTO);
}
