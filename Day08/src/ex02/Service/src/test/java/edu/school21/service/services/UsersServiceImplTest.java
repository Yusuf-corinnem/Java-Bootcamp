package edu.school21.service.services;

import edu.school21.service.config.TestApplicationConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest {
    @Test
    public void testSignUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        UsersService usersServiceImpl = context.getBean("usersServiceImpl", UsersService.class);
        assertNotNull(usersServiceImpl.signUp("test@example.com"), "Temp password is null");
    }
}