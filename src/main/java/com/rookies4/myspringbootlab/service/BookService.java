package com.rookies4.myspringbootlab.service;

import com.rookies4.myspringbootlab.dto.BookDTO;
import com.rookies4.myspringbootlab.entity.Book;
import com.rookies4.myspringbootlab.exception.BusinessException;
import com.rookies4.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDTO.BookResponse> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    public BookDTO.BookResponse getBookById(Long id){
        return bookRepository.findById(id)
                .map(BookDTO.BookResponse::from)
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다: " + id));
    }

    public BookDTO.BookResponse getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn)
                .map(BookDTO.BookResponse::from)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서를 찾을 수 없습니다: " + isbn));
    }

    public List<BookDTO.BookResponse> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author).stream()
                .map(BookDTO.BookResponse::from)
                .collect(Collectors.toList());
    }

    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request){
        return Optional.of(request.toEntity())
                .map(bookRepository::save)
                .map(BookDTO.BookResponse::from)
                .orElseThrow(() -> new BusinessException("도서 생성에 실패했습니다."));
    }

    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request){
        return bookRepository.findById(id)
                .map(book -> {
                    if(request.getTitle() != null)
                        book.setTitle(request.getTitle());
                    if(request.getAuthor() != null)
                        book.setAuthor(request.getAuthor());
                    if(request.getIsbn() != null)
                        book.setIsbn(request.getIsbn());
                    if(request.getPrice() != null)
                        book.setPrice(request.getPrice());
                    if(request.getPublishDate() != null)
                        book.setPublishDate(request.getPublishDate());
                    return book;
                })
                .map(bookRepository::save)
                .map(BookDTO.BookResponse::from)
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다: " + id));
    }

    public void deleteBook(Long id){
        bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return book;
                })
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다: " + id));
    }

}
