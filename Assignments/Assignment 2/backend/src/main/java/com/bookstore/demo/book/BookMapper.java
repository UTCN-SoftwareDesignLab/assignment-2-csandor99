package com.bookstore.demo.book;

import com.bookstore.demo.book.model.Book;
import com.bookstore.demo.book.model.dto.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book item);
    Book fromDto(BookDTO item);

}
