package com.bookstore.demo.report;

import com.bookstore.demo.book.BookMapper;
import com.bookstore.demo.book.BookService;
import com.bookstore.demo.book.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.bookstore.demo.report.ReportType.CSV;
import static com.bookstore.demo.report.ReportType.PDF;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Test
    void getReportService() {
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

        bookService.create(bookMapper.toDto(book1));
        bookService.create(bookMapper.toDto(book2));
        ReportService csvReportService = reportServiceFactory.getReportService(CSV);
        Assertions.assertEquals("I am a CSV reporter.", csvReportService.export());

        ReportService pdfReportService = reportServiceFactory.getReportService(PDF);
        Assertions.assertEquals("I am a PDF reporter.", pdfReportService.export());
    }
}