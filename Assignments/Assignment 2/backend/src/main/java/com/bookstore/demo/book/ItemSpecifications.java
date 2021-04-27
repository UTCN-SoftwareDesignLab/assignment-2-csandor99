package com.bookstore.demo.book;

import com.bookstore.demo.book.model.Book;
import com.bookstore.demo.book.model.dto.ItemFilterRequestDto;

import com.bookstore.demo.user.model.ERole;
import com.bookstore.demo.user.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;


import static java.util.Optional.ofNullable;

public class ItemSpecifications {

    public static Specification<Book> similarNames(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), name);
    }

    public static Specification<Book> createdAfter(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThan(root.get("dateCreated"), date);
    }

}
