package mylab.order.di.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:mylab-order-di.xml")
public class OrderSpringTest {
	
	@Autowired
	ShoppingCart shoppingCart;
	
	@Autowired
	OrderService orderService;
	
	@Test
	void test() {
		// null 검증
		assertNotNull(shoppingCart, "ShoppingCart Null");
		
		// product size 검증
		assertEquals(3, shoppingCart.getProducts().size());
		
		// 노트북 검증
		assertEquals("노트북", shoppingCart.getProducts().get(0).getName());
		
		// 스마트폰 검증
		assertEquals("스마트폰", shoppingCart.getProducts().get(1).getName());
		
	}
	
	@Test
	void testOrderService() {
		// orderService null 검증
		assertNotNull(orderService, "OrderService Null");
		
		// ShoppingCart OrderService에 주입됐는지
		assertNotNull(orderService.getShoppingCart(), "orderService안에 쇼핑카트가 Null");
	}
}
