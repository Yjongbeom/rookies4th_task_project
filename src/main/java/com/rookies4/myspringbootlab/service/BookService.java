package com.rookies4.myspringbootlab.service;

import com.rookies4.myspringbootlab.dto.BookDTO;
import com.rookies4.myspringbootlab.entity.Book;
import com.rookies4.myspringbootlab.entity.BookDetail;
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

    public List<BookDTO.Response> getAllBooks(){
        return bookRepository.findAll().stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public BookDTO.Response getBookById(Long id){
        return bookRepository.findById(id)
                .map(BookDTO.Response::fromEntity)
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다: " + id));
    }

    public BookDTO.Response getBookByIsbn(String isbn){
        return bookRepository.findByIsbn(isbn)
                .map(BookDTO.Response::fromEntity)
                .orElseThrow(() -> new BusinessException("해당 ISBN의 도서를 찾을 수 없습니다: " + isbn));
    }

    public List<BookDTO.Response> getBooksByAuthor(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public List<BookDTO.Response> getBooksByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(BookDTO.Response::fromEntity)
                .collect(Collectors.toList());
    }

    public BookDTO.Response createBook(BookDTO.Request request){
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException("이미 존재하는 ISBN입니다: " + request.getIsbn());
        }

        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getIsbn(),
                request.getPublishDate(),
                request.getPrice()
        );

        if (request.getDetailRequest() != null) {
            BookDetail detail = BookDetail.builder()
                    .description(request.getDetailRequest().getDescription())
                    .language(request.getDetailRequest().getLanguage())
                    .pageCount(request.getDetailRequest().getPageCount())
                    .publisher(request.getDetailRequest().getPublisher())
                    .coverImageUrl(request.getDetailRequest().getCoverImageUrl())
                    .edition(request.getDetailRequest().getEdition())
                    .book(book)
                    .build();
            book.setBookDetail(detail);
        }

        Book saved = bookRepository.save(book);
        return BookDTO.Response.fromEntity(saved);

    }

    public BookDTO.Response updateBook(Long id, BookDTO.Request request){
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

                    if (request.getDetailRequest() != null) {
                        BookDetail detail = book.getBookDetail();
                        if (detail == null) {
                            detail = BookDetail.builder().book(book).build();
                            book.setBookDetail(detail);
                        }
                        detail.setDescription(request.getDetailRequest().getDescription());
                        detail.setLanguage(request.getDetailRequest().getLanguage());
                        detail.setPageCount(request.getDetailRequest().getPageCount());
                        detail.setPublisher(request.getDetailRequest().getPublisher());
                        detail.setCoverImageUrl(request.getDetailRequest().getCoverImageUrl());
                        detail.setEdition(request.getDetailRequest().getEdition());
                    }


                    return book;
                })
                .map(bookRepository::save)
                .map(BookDTO.Response::fromEntity)
                .orElseThrow(() -> new BusinessException("해당 Id의 도서를 찾을 수 없습니다: " + id));
    }

    public void deleteBook(Long id){
        bookRepository.findById(id)
                .ifPresentOrElse(bookRepository::delete,
                () -> {throw new BusinessException("해당 Id의 도서를 찾을 수 없습니다: " + id);
                });
    }

}
