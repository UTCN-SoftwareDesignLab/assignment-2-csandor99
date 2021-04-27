package com.bookstore.demo.book;

import com.bookstore.demo.TestCreationFactory;
import com.bookstore.demo.book.model.Book;
import com.bookstore.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> items = TestCreationFactory.listOf(Book.class);
        bookRepository.saveAll(items);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(items.size(), all.size());
    }

    @Test
    void edit(){
        BookDTO book = BookDTO.builder()
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(10)
                .quantity(10)
                .build();

        BookDTO returned = bookService.create(book);
        returned.setTitle("New title");

        BookDTO newBook = bookService.edit(returned);

        Assertions.assertEquals(book.getAuthor(), newBook.getAuthor());
    }

    @Test
    void delete(){
        BookDTO book = BookDTO.builder()
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(10)
                .quantity(10)
                .build();

        BookDTO newBook = bookService.create(book);
        Assertions.assertEquals(1, bookService.findAll().size());

        bookService.delete(newBook.getId());
        Assertions.assertEquals(0, bookService.findAll().size());
    }

    @Test
    void sell(){
        BookDTO book = BookDTO.builder()
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(10)
                .quantity(10)
                .build();

        BookDTO returned = bookService.create(book);

        BookDTO newBook = bookService.sell(returned.getId());

        Assertions.assertEquals(returned.getQuantity()-1, newBook.getQuantity());
    }
}