package edu.school21.service.application;

import edu.school21.service.repositories.UsersRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbcImpl", UsersRepository.class);
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
        System.out.println(usersRepository.findAll());
    }
}