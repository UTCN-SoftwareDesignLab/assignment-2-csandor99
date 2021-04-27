package com.bookstore.demo.book;

import com.bookstore.demo.TestCreationFactory;
import com.bookstore.demo.book.model.Book;
import com.bookstore.demo.user.RoleRepository;
import com.bookstore.demo.user.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder()
                .title("title1")
                .author("author1")
                .genre("genre")
                .price(10.0)
                .quantity(1)
                .build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        repository.saveAll(books);
        List<Book> all = repository.findAll();
        assertEquals(books.size(), all.size());
    }

    @Test
    public void testSimpleLikeQuery() {
        final Book book1 = Book.builder()
                .title("Stewie")
                .author("author1")
                .genre("genre")
                .price(10.0)
                .quantity(1)
                .build();

        repository.save(book1);

        final List<Book> res1 = repository.findAllByTitleContainingOrAuthorContainingOrGenreContaining("Stewie",
                "noooope","horror");
        assertFalse(res1.isEmpty());
        assertEquals(1, res1.size());
        assertEquals(book1.getId(), res1.get(0).getId());

        final List<Book> res2 = repository.findAllByTitleContainingOrAuthorContainingOrGenreContaining("tew",
                "noooope","horror");
        assertFalse(res2.isEmpty());
        assertEquals(1, res2.size());
        assertEquals(book1.getId(), res2.get(0).getId());

        // what if we wanted sorting by default on these queries?


        // what if we need more complex queries like ... give me all the items with at least 1 excellent review?
    }

    @Test
    public void OOSTest(){
        final Book book1 = Book.builder()
                .title("Stewie")
                .author("author1")
                .genre("genre")
                .price(10.0)
                .quantity(0)
                .build();
        final Book book2 = Book.builder()
                .title("Stewie")
                .author("author1")
                .genre("genre")
                .price(10.0)
                .quantity(1)
                .build();

        repository.save(book1);
        repository.save(book2);

        List<Book> list = repository.findAllByQuantityEquals(0);

        assertEquals(1,list.size());
    }


}
