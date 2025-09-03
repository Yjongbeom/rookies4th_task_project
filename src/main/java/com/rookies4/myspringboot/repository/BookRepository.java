package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // findByIsbn(String isbn), findByAuthor(String author) 메소드 추가

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByAuthor(String author);
}
