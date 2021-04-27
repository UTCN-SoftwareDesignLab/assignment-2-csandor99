package com.bookstore.demo.book;

import com.bookstore.demo.TestCreationFactory;
import com.bookstore.demo.book.model.Book;
import com.bookstore.demo.book.model.dto.BookDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }


    @Test
    void create(){
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .genre("Genre")
                .price(5)
                .quantity(8)
                .build();

        when(bookRepository.save(book)).thenReturn(book);

        BookDTO bookDTO = bookService.create(bookMapper.toDto(book));

        Assertions.assertEquals(bookMapper.toDto(book),bookDTO);

    }



}