package mylab.order.di.xml;

import java.util.List;

public class ShoppingCart {
    private List<Product> products;
    
    public ShoppingCart() {}
    
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
    
    public double getTotalPrice() {
    	double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
        //return products.stream().mapToDouble(Product::getPrice).sum();
    }
    
    @Override
    public String toString() {
        return "ShoppingCart [products=" + products + "]";
    }
}