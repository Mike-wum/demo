package com.demo.bookstore.service;

import com.demo.bookstore.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO create(BookDTO bookDTO);
    void delete(Long id);
    BookDTO update(BookDTO bookDTO);
    List<BookDTO> findAll();
    BookDTO find(Long id);
}
