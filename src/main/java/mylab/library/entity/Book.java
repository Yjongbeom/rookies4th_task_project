package mylab.library.entity;

public class Book {
	private String title;
	private String author;
	private String isbn;
	private int publishYear;
	private boolean isAvailable;
	
	public Book() {
		isAvailable = true;
	}

	public Book(String title, String author, String isbn, int publishYear) {
		this();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.publishYear = publishYear;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}
	
	public boolean checkOut() {
		if(isAvailable) {
			isAvailable = false;
			return true;
		}
		return false;
	}
	
	public void returnBook() {
		isAvailable = true;
	}
	
	@Override
	public String toString() {
		String flag_message = isAvailable ? "가능" : "대출 중";
		return "책 제목: " + title + "\t" + 
				"저자: " + author + "\t" + 
				"ISBN: " + isbn + 
				" 출판년도: " + publishYear + 
				"	대출 가능 여부 : " + flag_message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}
	
	
	
	
}
