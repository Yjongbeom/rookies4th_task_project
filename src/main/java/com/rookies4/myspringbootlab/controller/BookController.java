package com.rookies4.myspringbootlab.controller;

import com.rookies4.myspringbootlab.dto.BookDTO;
import com.rookies4.myspringbootlab.entity.Book;
import com.rookies4.myspringbootlab.exception.BusinessException;
import com.rookies4.myspringbootlab.repository.BookRepository;
import com.rookies4.myspringbootlab.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
//    private final BookRepository bookRepository;
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO.Response>> getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO.Response> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO.Response> getBookByIsbn(@PathVariable String isbn){
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @GetMapping("/search/author/{author}")
    public ResponseEntity<List<BookDTO.Response>> getBooksByAuthor(@PathVariable String author){
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<BookDTO.Response>> getBooksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @PostMapping
    public ResponseEntity<BookDTO.Response> createBook(@RequestBody BookDTO.Request request){
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO.Response> updateBook(@PathVariable Long id, @RequestBody BookDTO.Request request){
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
