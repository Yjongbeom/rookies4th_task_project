package mylab.library.control;

import java.util.List;

import mylab.library.entity.Book;
import mylab.library.entity.Library;

public class LibraryManagementSystem {
	public static void main(String[] args) {
		Library library = new Library("육종범");
		addSampleBooks(library);
		
		System.out.println("===== 중앙 도서관 =====");
		displayCountBooks(library);
		
		System.out.println();
		
		System.out.println("===== 도서 검색 테스트 =====");
		testFindBook(library);
		
		System.out.println();
		
		System.out.println("===== 도서 대출 테스트 =====");
		testCheckOut(library);
		
		System.out.println();
		
		System.out.println("===== 도서 반납 테스트 ======");
		testReturn(library);
		
		System.out.println();
		
		System.out.println("===== 대출 가능한 도서 목록 =====");
		displayAvailableBooks(library);
		
	}
	
	private static void addSampleBooks(Library library) {
        library.addBook(new Book("자바 프로그래밍", "김자바", "978-89-01-12345-6", 2022));
        library.addBook(new Book("객체지향의 사실과 오해", "조영호", "978-89-01-67890-1", 2015));
        library.addBook(new Book("Clean Code", "Robert C. Martin", "978-0-13-235088-4", 2008));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "978-0-13-468599-1", 2018));
        library.addBook(new Book("Head First Java", "Kathy Sierra", "978-0-596-00920-5", 2005));
        library.addBook(new Book("자바의 정석", "남궁성", "978-89-01-14077-4", 2019));
	}
	
	private static void testFindBook(Library library) {
		System.out.println("제목으로 검색 결과:");
		System.out.println(library.findBookByTitle("자바의 정석"));
		System.out.println();
		
		System.out.println("저자로 검색 결과:");
		System.out.println(library.findBookByAuthor("Robert C. Martin"));
	}
	
	private static void testCheckOut(Library library) {
		String isbn = "978-89-01-12345-6";
		boolean flag = library.checkOutBook(isbn);
		
		if(flag) {
			System.out.println("도서 대출 성공!");
			System.out.println("대출된 도서 정보:");
			System.out.println(library.findBookByISBN(isbn));
			System.out.println();
			
			
			System.out.println("도서관 현재 상태:");
			displayCountBooks(library);
		}
		
		else {
			System.out.println("이미 대출중인 도서");
			System.out.println("도서관 현재 상태:");
			displayCountBooks(library);
		}
		
	}
	
	private static void testReturn(Library library) {
		String isbn = "978-89-01-12345-6";
		boolean flag = library.returnBook(isbn);
		
		if(flag) {
			System.out.println("도서 반납 성공!");
			System.out.println("반납된 도서 정보:");
			System.out.println(library.findBookByISBN(isbn));
			System.out.println();
			
			System.out.println("도서관 현재 상태:");
			displayCountBooks(library);
		}
		else {
			System.out.println("도서를 찾을 수 없습니다.");
			System.out.println("도서관 현재 상태:");
			displayCountBooks(library);
		}
		
	}
	
	// 중복되는 코드가 많아 추가
	private static void displayCountBooks(Library library) {
		System.out.println("전체 도서 수: " + library.getTotalBooks());
		System.out.println("대출 가능 도서 수: " + library.getAvailableBooksCount());
		System.out.println("대출 중인 도서 수: " + library.getBorrowedBooksCount());
	}
	
	private static void displayAvailableBooks(Library library) {
		List<Book> books = library.getAvailableBooks();
		
		for(Book book : books) {
			System.out.println(book);
			System.out.println("------------------------");
		}
	}

}