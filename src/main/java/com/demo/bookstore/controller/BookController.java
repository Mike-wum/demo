package com.demo.bookstore.controller;

import com.demo.bookstore.dto.BookDTO;
import com.demo.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDTO> query() {
        return bookService.findAll();
    }

    @GetMapping(value = "/{id}")
    public BookDTO get(@PathVariable("id") Long id) {
        return bookService.find(id);
    }

    @PostMapping
    public ResponseEntity<BookDTO> add(@RequestBody BookDTO bookDTO) {
        log.info("Book Add..");
        BookDTO dto = bookService.create(bookDTO);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public BookDTO update(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
        bookDTO.setId(id);
        return bookService.update(bookDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
