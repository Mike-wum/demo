package com.demo.bookstore.service.impl;

import com.demo.bookstore.common.exception.NoStockException;
import com.demo.bookstore.common.util.BeanUtil;
import com.demo.bookstore.dao.InventoryDao;
import com.demo.bookstore.dto.InventoryDTO;
import com.demo.bookstore.entity.Inventory;
import com.demo.bookstore.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InventoryDao inventoryDao;

    @Override
    public boolean checkStock(Long bookId, Integer quantity) {
        Inventory inventory = inventoryDao.findByBookId(bookId);
        if (inventory != null && inventory.getStock() >= quantity) {
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void reduceStock(Long bookId, Integer quantity) throws NoStockException {
        int count = inventoryDao.reduceStock(bookId, quantity);
        if(count == 0) {
            log.info("book:{} out of stock!", bookId);
            throw new NoStockException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void increaseStock(Long bookId, Integer quantity) {
        inventoryDao.increaseStock(bookId, quantity);
    }

    @Override
    public InventoryDTO create(InventoryDTO inventoryDTO) {
        Inventory inventory = BeanUtil.copyProperties(inventoryDTO, new Inventory());
        inventory = inventoryDao.save(inventory);
        return BeanUtil.copyProperties(inventory, inventoryDTO);
    }
}
