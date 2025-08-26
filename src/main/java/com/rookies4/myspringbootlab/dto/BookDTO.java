package com.rookies4.myspringbootlab.dto;

import com.rookies4.myspringbootlab.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class BookDTO {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookCreateRequest{
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public Book toEntity(){
            return new Book(title, author, isbn, publishDate, price);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookUpdateRequest{
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookResponse{
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book book){
            return new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getPrice(),
                    book.getPublishDate()
            );
        }
    }
}
