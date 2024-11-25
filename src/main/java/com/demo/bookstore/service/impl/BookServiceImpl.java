package com.demo.bookstore.service.impl;

import com.demo.bookstore.common.util.BeanUtil;
import com.demo.bookstore.dao.BookDao;
import com.demo.bookstore.dto.BookDTO;
import com.demo.bookstore.entity.Book;
import com.demo.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookDao bookDao;

    @Override
    public BookDTO create(BookDTO bookDTO) {
        return this.save(bookDTO);
    }

    @CacheEvict(value = "books", key = "#id")
    @Override
    public void delete(Long id) {
        bookDao.deleteById(id);
    }

    private BookDTO save(BookDTO bookDTO) {
        Book book = BeanUtil.copyProperties(bookDTO, new Book());
        book = bookDao.save(book);
        return BeanUtil.copyProperties(book, bookDTO);
    }

    @CacheEvict(value = "books", key = "#bookDTO.id")
    @Override
    public BookDTO update(BookDTO bookDTO) {
        return this.save(bookDTO);
    }

    @Override
    public List<BookDTO> findAll() {
        List<Book> list = bookDao.findAll();
        return BeanUtil.copyListProperties(list, BookDTO::new);
    }

    @Cacheable(value = "books", key = "#id", unless = "#result == null")
    @Override
    public BookDTO find(Long id) {
        log.info("find book..");
        Optional<Book> opt = bookDao.findById(id);
        if (opt.isPresent()) {
            return BeanUtil.copyProperties(opt.get(), new BookDTO());
        }
        return null;
    }
}
