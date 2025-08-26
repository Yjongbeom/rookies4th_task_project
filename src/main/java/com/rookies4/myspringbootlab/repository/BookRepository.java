package com.rookies4.myspringbootlab.repository;

import com.rookies4.myspringbootlab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);

    Optional<Book> findById(Long id);
    boolean existsByIsbn(String isbn);
}
