package com.bookstore.demo.book;

import com.bookstore.demo.book.model.Book;
import com.bookstore.demo.book.model.dto.BookDTO;
import com.bookstore.demo.book.model.dto.ItemFilterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> filteredBooks(String filter){
        return bookRepository.findAllByTitleContainingOrAuthorContainingOrGenreContaining(filter,filter,filter).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(BookDTO book) {
        Book updatedBook = findById(book.getId());
        updatedBook.setTitle(book.getTitle());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setGenre(book.getGenre());
        updatedBook.setPrice(book.getPrice());
        updatedBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(updatedBook)
        );
    }

    public BookDTO sell(Long id) {
        Book myBook = findById(id);
        if(myBook.getQuantity() > 0){
            myBook.setQuantity(myBook.getQuantity()-1);
        }
        return bookMapper.toDto(
                bookRepository.save(myBook)
        );
    }

    public List<BookDTO> outOfStock(){
        return bookRepository.findAllByQuantityEquals(0).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO get(Long id) {
        return bookMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}
