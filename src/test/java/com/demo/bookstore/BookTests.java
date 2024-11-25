package com.demo.bookstore;

import com.demo.bookstore.common.enums.BookCategory;
import com.demo.bookstore.dto.BookDTO;
import com.demo.bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTests extends BaseTest{
    @Autowired
    private BookService bookService;

    @Test
    public void testQueryOne() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<BookDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/book/1",
                HttpMethod.GET,
                entity,
                BookDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("bookname1");
        assertThat(response.getBody().getAuthor()).isEqualTo("author1");
        assertThat(response.getBody().getPublisher()).isEqualTo("pub1");
        assertThat(response.getBody().getSn()).isEqualTo("sn1");
        assertThat(response.getBody().getCategory()).isEqualTo(BookCategory.FICTION);
        assertThat(response.getBody().getPrice()).isEqualTo(new BigDecimal("100.00"));
    }

    @Test
    public void testQueryAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/book",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<BookDTO>>(){}
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void testAdd() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName("mybook");
        bookDTO.setAuthor("myauthor");
        bookDTO.setCategory(BookCategory.FICTION);
        bookDTO.setPublisher("mypublisher");
        bookDTO.setSn("mysn");
        bookDTO.setPrice(new BigDecimal(100.00));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<BookDTO> entity = new HttpEntity<>(bookDTO, headers);
        ResponseEntity<BookDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/book",
                HttpMethod.POST,
                entity,
                BookDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("mybook");
        assertThat(response.getBody().getAuthor()).isEqualTo("myauthor");
        assertThat(response.getBody().getPublisher()).isEqualTo("mypublisher");
        assertThat(response.getBody().getSn()).isEqualTo("mysn");
        assertThat(response.getBody().getCategory()).isEqualTo(BookCategory.FICTION);
        assertThat(response.getBody().getPrice()).isEqualTo(new BigDecimal(100.00));
    }

    @Test
    public void testUpdate() {
        BookDTO bookDTO = bookService.find(3L);
        bookDTO.setAuthor("authornew");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<BookDTO> entity = new HttpEntity<>(bookDTO, headers);
        ResponseEntity<BookDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/book/3",
                HttpMethod.PUT,
                entity,
                BookDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAuthor()).isEqualTo("authornew");
    }

    @Test
    public void testDelete() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/book/3",
                HttpMethod.DELETE,
                entity,
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
