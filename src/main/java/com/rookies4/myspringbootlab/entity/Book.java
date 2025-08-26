package com.rookies4.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@DynamicUpdate
@Table(name="books")
public class Book {
    // id(Long), title(String), author(String), isbn(String),
    //publishDate(LocalDate), price(Integer)


    public Book(String title, String author, String isbn, LocalDate publishDate, Integer price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.price = price;
    }

    public Book(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    @Column(unique = true)
    private String isbn;

    private LocalDate publishDate;

    private Integer price;
}
