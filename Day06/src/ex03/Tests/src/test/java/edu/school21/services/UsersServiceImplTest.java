package edu.school21.services;

import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UsersServiceImplTest {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void correctLoginPassword_Test() {
        User user = new User(1L, "login", "password");
        Mockito.when(usersRepository.findByLogin("login")).thenReturn(user);
        Assertions.assertTrue(usersService.authenticate("login", "password"));
        Assertions.assertTrue(user.isAuthenticationSuccessStatus());
        Mockito.verify(usersRepository, Mockito.times(1)).findByLogin("login");
    }

    @Test
    public void wrongLogin_Test() {
        Mockito.when(usersRepository.findByLogin("login")).thenThrow(new EntityNotFoundException("User not found"));
        Assertions.assertFalse(usersService.authenticate("login", "password"));
        Mockito.verify(usersRepository, Mockito.times(1)).findByLogin("login");
    }

    @Test
    public void wrongPassword_Test() {
        User user = new User(1L, "login", "password");
        Mockito.when(usersRepository.findByLogin("login")).thenReturn(user);
        Assertions.assertFalse(usersService.authenticate("login", "password1"));
        Mockito.verify(usersRepository, Mockito.times(1)).findByLogin("login");
    }
}
