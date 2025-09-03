package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@RequiredArgsConstructor
public class BookRepositoryTest {

//    private final BookRepository bookRepository;

    @Autowired
    private BookRepository bookRepository;

    // 도서 등록 테스트 ( testCreateBook() )
    //ISBN으로 도서 조회 테스트 ( testFindByIsbn() )
    //저자명으로 도서 목록 조회 테스트 ( testFindByAuthor() )
    //도서 정보 수정 테스트 ( testUpdateBook() )
    //도서 삭제 테스트 ( testDeleteBook() )

    @Test
    @Rollback(value = false)
    public void testCreateBook(){
        Book book = new Book( "스프링 부트 입문", "홍길동", "9788956746425", LocalDate.parse("2025-05-07"), 30000);
        // "스프링 부트 입문"
        //"홍길동"
        //"9788956746425"
        //30000
        //2025-05-07

        Book saved = bookRepository.save(book);
        assertEquals("스프링 부트 입문", saved.getTitle());
    }

    //ISBN으로 도서 조회 테스트 ( testFindByIsbn() )
    @Test
    public void testFindByIsbn(){
//        Optional<Book> isbn = bookRepository.findByIsbn(book.getIsbn());
//        bookRepository.findByAuthor(book.getAuthor());
        Book book = new Book( "스프링 부트 입문", "홍길동", "97889567464555525", LocalDate.parse("2025-05-07"), 30000);
        bookRepository.save(book);

        Optional<Book> book1 = bookRepository.findByIsbn("97889567464555525");
        Book book2 = book1.get();
        System.out.println(book2);


        // update
        book2.setIsbn("fsdfasd");
        bookRepository.save(book2);

        // delete
        bookRepository.delete(book2);
    }

    //도서 정보 수정 테스트 ( testUpdateBook() )

    //도서 삭제 테스트 ( testDeleteBook() )



}
