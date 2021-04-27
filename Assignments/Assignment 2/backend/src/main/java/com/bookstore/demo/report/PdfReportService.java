package com.bookstore.demo.report;

import com.bookstore.demo.book.BookService;
import com.bookstore.demo.book.model.Book;
import com.bookstore.demo.book.model.dto.BookDTO;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.bookstore.demo.report.ReportType.PDF;

@Service
@AllArgsConstructor
public class PdfReportService implements ReportService {

    private BookService bookService;

    @Override
    public String export() {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        List<BookDTO> books = bookService.outOfStock();

        try {
            PDPageContentStream stream = new PDPageContentStream(doc,page);
            stream.setFont(PDType1Font.COURIER, 12);
            stream.beginText();
            stream.setLeading(14.5f);
            stream.newLineAtOffset(25, 700);
            stream.showText("Report: ");
            stream.newLine();
            stream.newLine();

            for(BookDTO book: books) {
                stream.showText("ID: " + book.getId());
                stream.newLine();
                stream.showText("Title: " + book.getTitle());
                stream.newLine();
                stream.showText("Author: " + book.getAuthor());
                stream.newLine();
                stream.showText("Genre: " + book.getGenre());
                stream.newLine();
                stream.showText("Price: " + book.getPrice());
                stream.newLine();
                stream.showText("####################################################################");
                stream.newLine();
            }

            stream.endText();
            stream.close();
            doc.save("ReportPDF.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
