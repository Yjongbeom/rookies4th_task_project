package mylab.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:mylab-user-di.xml")
public class UserServiceTest {
	@Autowired
    private UserService userService;

    @Test
    public void testDependencyInjection() {
        assertNotNull(userService, "UserService 주입 실패");
        
        UserRepository userRepo = userService.getUserRepository();
        assertNotNull(userRepo, "UserRepository 주입 실패");
        assertEquals("MySQL", userRepo.getDbType(), "DB 타입 불일치");
        
        assertNotNull(userService.getSecurityService(), "SecurityService 주입 실패");
    }

    @Test
    public void testRegisterUser() {
        boolean result = userService.registerUser("user01", "홍길동", "pass123");
        assertTrue(result, "사용자 등록 실패");
    }
}
