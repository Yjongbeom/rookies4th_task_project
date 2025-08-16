package mylab.book.entity;

public class Publication {
	private String title;
	private String publishedDate;
	private int page;
	private int price;
	
	public Publication() {}
	
	public Publication(String title, String publishedDate, int page, int price) {
        this.title = title;
        this.publishedDate = publishedDate;
        this.page = page;
        this.price = price;
    }
	
	public String getTitle() { 
		return title; 
	}
	
    public void setTitle(String title) {
    	this.title = title; 
    
    }
    public String getPublishedDate() {
    	return publishedDate;
    
    }
    public void setPublishedDate(String publishedDate) {
    	this.publishedDate = publishedDate;
    }
    
    public int getPage() {
    	return page;
    }
    
    public void setPage(int page) {
    	this.page = page;
    }
    
    public int getPrice() {
    	return price;
    }
    
    public void setPrice(int price) {
    	this.price = price;
    }
    
    @Override
    public String toString() {
        return title;
    }
}
