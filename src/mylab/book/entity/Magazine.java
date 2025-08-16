package mylab.book.entity;

public class Magazine extends Publication {
    private String publishPeriod;

    public Magazine() {}

    public Magazine(String title, String publishedDate, int page, int price, 
                    String publishPeriod) {
        super(title, publishedDate, page, price);
        this.publishPeriod = publishPeriod;
    }

    public String getPublishPeriod() {
    	return publishPeriod;
    }
    
    public void setPublishPeriod(String publishPeriod) {
    	this.publishPeriod = publishPeriod;
    }

    @Override
    public String toString() {
        return super.toString() + " [잡지] 발행주기:" + publishPeriod + ", " 
                + getPage() + "쪽, " + getPrice() + "원, 출판일:" + getPublishedDate();
    }
}
