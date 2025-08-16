package mylab.book.entity;

public class ReferenceBook extends Publication {
    private String field;

    public ReferenceBook() {}

    public ReferenceBook(String title, String publishedDate, int page, int price, 
                         String field) {
        super(title, publishedDate, page, price);
        this.field = field;
    }

    public String getField() {
    	return field;
    }
    
    public void setField(String field) {
    	this.field = field;
    }

    @Override
    public String toString() {
        return super.toString() + " [참고서] 분야:" + field + ", " 
                + getPage() + "쪽, " + getPrice() + "원, 출판일:" + getPublishedDate();
    }
}