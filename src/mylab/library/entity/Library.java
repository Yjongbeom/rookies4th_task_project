package mylab.library.entity;

import java.util.ArrayList;
import java.util.List;

public class Library {
	private String name;
	private List<Book> books;
	
	public Library(String name) {
		this.name = name;
		this.books = new ArrayList<>();
	}
	
	public void addBook(Book book) {
		books.add(book);
		System.out.println("도서가 추가되었습니다: " + book.getTitle());
	}
	
	public Book findBookByTitle(String title) {
		for(Book book : books) {
			if(book.getTitle().equals(title))
				return book;
		}
		return null;
	}
	
	public Book findBookByAuthor(String author) {
		for(Book book : books) {
			if(book.getAuthor().equals(author))
				return book;
		}
		return null;
	}
	
	public Book findBookByISBN(String isbn) {
		for(Book book : books) {
			if(book.getIsbn().equals(isbn))
				return book;
		}
		return null;
	}
	
	public boolean checkOutBook(String isbn) {
		Book book = findBookByISBN(isbn);
	
		return book.checkOut();
	}
	
	public boolean returnBook(String isbn) {
		Book book = findBookByISBN(isbn);
		
		if(!book.isAvailable()) {
			book.returnBook();
			return true;
		}
		return false;
	}
	
	public List<Book> getAvailableBooks(){
		List<Book> lists = new ArrayList<>();
		
		for(Book book : books) {
			if(book.isAvailable())
				lists.add(book);
		}
		return lists;
	}
	
	public List<Book> getAllBooks(){
		return books;
	}
	
	public int getTotalBooks(){
		return books.size();
	}
	
	public int getAvailableBooksCount(){
		return getAvailableBooks().size();
	}
	
	public int getBorrowedBooksCount(){
		return books.size() - getAvailableBooksCount();
	}
}
