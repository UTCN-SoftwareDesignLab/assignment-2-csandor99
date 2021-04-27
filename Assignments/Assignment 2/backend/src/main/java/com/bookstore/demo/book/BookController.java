package com.bookstore.demo.book;

import com.bookstore.demo.book.model.dto.BookDTO;
import com.bookstore.demo.report.ReportServiceFactory;
import com.bookstore.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bookstore.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @GetMapping(SEARCH)
    public List<BookDTO> filteredBooks(@PathVariable String filter){ return bookService.filteredBooks(filter);}

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping
    public BookDTO edit(@RequestBody BookDTO book) {
        return bookService.edit(book);
    }

    @PatchMapping(ENTITY)
    public BookDTO sell(@PathVariable Long id){ return bookService.sell(id);}

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping(ENTITY)
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.get(id);
    }

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
