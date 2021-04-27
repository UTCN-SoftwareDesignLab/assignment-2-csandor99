package com.bookstore.demo.report;

import com.bookstore.demo.book.BookService;
import com.bookstore.demo.book.model.dto.BookDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.bookstore.demo.report.ReportType.CSV;

@Service
@AllArgsConstructor
public class CSVReportService implements ReportService {

    private final BookService bookService;

    @Override
    public String export() {
        List<BookDTO> books = bookService.outOfStock();
        try{
            FileWriter fw = new FileWriter("ReportCSV.csv");
            for(BookDTO book: books){
                fw.append(book.getId().toString());
                fw.append(",");
                fw.append(book.getTitle());
                fw.append(",");
                fw.append(book.getAuthor());
                fw.append(",");
                fw.append(book.getGenre());
                fw.append(",");
                fw.append(String.valueOf(book.getPrice()));
                fw.append("\n");
            }
            fw.close();
        }catch(IOException e){
            return "Error";
        }
        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
