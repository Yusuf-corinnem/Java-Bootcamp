package edu.school21.service.services;

import edu.school21.service.model.User;
import edu.school21.service.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository<User> usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("usersRepositoryJdbcTemplateImpl") UsersRepository<User> usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public String signUp(String email) {
        String tempPassword = "temp-" + System.currentTimeMillis();
        usersRepository.save(new User(email, tempPassword));
        return tempPassword;
    }
}
