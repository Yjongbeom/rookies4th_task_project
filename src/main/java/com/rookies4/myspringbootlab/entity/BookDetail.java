package com.rookies4.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "book_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String language;
    private Integer pageCount;
    private String publisher;
    private String coverImageUrl;
    private String edition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
}
