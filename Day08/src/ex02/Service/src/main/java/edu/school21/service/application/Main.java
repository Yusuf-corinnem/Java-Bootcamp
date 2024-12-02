package edu.school21.service.application;

import edu.school21.service.config.ApplicationConfig;
import edu.school21.service.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        UsersService usersService = context.getBean("usersServiceImpl", UsersService.class);
        usersService.signUp("corinnem@student.21-school.ru");
    }
}