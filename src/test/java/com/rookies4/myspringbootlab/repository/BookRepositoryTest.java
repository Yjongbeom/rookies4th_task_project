package com.rookies4.myspringbootlab.repository;

import com.rookies4.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
    @Rollback(false)
    public void testCreateBook(){
        Book book = new Book( "스프링 부트 입문", "홍길동", "9788956746425", LocalDate.parse("2025-05-07"), 30000);
        Book book2 = new Book("JPA 프로그래밍", "박둘리", "9788956746432", LocalDate.parse("2025-04-30"), 35000);
        // "스프링 부트 입문"
        //"홍길동"
        //"9788956746425"
        //30000
        //2025-05-07

        Book saved = bookRepository.save(book);
        Book saved2 = bookRepository.save(book2);

        assertNotNull(saved.getId());
        assertNotNull(saved2.getId());
        assertEquals("스프링 부트 입문", saved.getTitle());
        assertEquals("9788956746425", saved2.getIsbn());
    }

    //ISBN으로 도서 조회 테스트 ( testFindByIsbn() )
    @Test
    @Rollback(value = false)
    public void testFindByIsbn(){
//        Optional<Book> isbn = bookRepository.findByIsbn(book.getIsbn());
//        bookRepository.findByAuthor(book.getAuthor());
        Optional<Book> bookOpt = bookRepository.findByIsbn("9788956746425");
        assertTrue(bookOpt.isPresent());
        assertEquals("홍길동", bookOpt.get().getAuthor());
    }

    //도서 정보 수정 테스트 ( testUpdateBook() )
    @Test
    @Rollback(value = false)
    public void testUpdateBook(){
        Optional<Book> bookOpt = bookRepository.findByIsbn("9788956746425");
        assertTrue(bookOpt.isPresent());
        Book book = bookOpt.get();
        book.setIsbn("12345678");
        bookRepository.save(book);
    }

    //도서 삭제 테스트 ( testDeleteBook() )
    @Test
    @Rollback(value = false)
    public void testDeleteBook(){
        Optional<Book> bookOpt = bookRepository.findByIsbn("12345678");
        assertTrue(bookOpt.isPresent());
        bookRepository.delete(bookOpt.get());
    }
}

