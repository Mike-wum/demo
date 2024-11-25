package com.demo.bookstore;

import com.demo.bookstore.dao.InventoryDao;
import com.demo.bookstore.dto.BookDTO;
import com.demo.bookstore.dto.InventoryCheckDTO;
import com.demo.bookstore.dto.InventoryDTO;
import com.demo.bookstore.dto.InventoryQueryDTO;
import com.demo.bookstore.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class InventoryTests extends BaseTest{

    @Autowired
    private InventoryDao inventoryDao;

    @Test
    public void testCheck(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        InventoryQueryDTO queryDTO = new InventoryQueryDTO();
        queryDTO.setBookId(1L);
        queryDTO.setQuantity(2);

        HttpEntity<InventoryQueryDTO> entity = new HttpEntity<>(queryDTO, headers);
        ResponseEntity<InventoryCheckDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/inventory/check",
                HttpMethod.POST,
                entity,
                InventoryCheckDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().isAvailable()).isEqualTo(true);
    }

    @Test
    public void testReduce() {
        int stockBefore = inventoryDao.findById(1L).get().getStock();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        InventoryQueryDTO queryDTO = new InventoryQueryDTO();
        queryDTO.setBookId(1L);
        queryDTO.setQuantity(2);

        HttpEntity<InventoryQueryDTO> entity = new HttpEntity<>(queryDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/inventory/reduce",
                HttpMethod.PUT,
                entity,
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        int stockAfter = inventoryDao.findById(1L).get().getStock();
        assertThat(stockAfter).isEqualTo(stockBefore - 2);
    }

    @Test
    public void testIncrease() {
        int stockBefore = inventoryDao.findById(1L).get().getStock();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        InventoryQueryDTO queryDTO = new InventoryQueryDTO();
        queryDTO.setBookId(1L);
        queryDTO.setQuantity(2);

        HttpEntity<InventoryQueryDTO> entity = new HttpEntity<>(queryDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/inventory/increase",
                HttpMethod.PUT,
                entity,
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        int stockAfter = inventoryDao.findById(1L).get().getStock();
        assertThat(stockAfter).isEqualTo(stockBefore + 2);
    }
}
