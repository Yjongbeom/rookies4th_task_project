package mylab.order.di.xml;
public class OrderService {
    private ShoppingCart shoppingCart;
    
    public OrderService() {}
    
    public ShoppingCart getShoppingCart() { 
    	return shoppingCart; 
    }
    
    public void setShoppingCart(ShoppingCart shoppingCart) { 
    	this.shoppingCart = shoppingCart; 
    }
    
    public double calculateOrderTotal() {
        return shoppingCart.getTotalPrice();
    }
    
    @Override
    public String toString() {
        return "OrderService [shoppingCart=" + shoppingCart + "]";
    }
}