package com.binar.kelompok3.secondhand.service.users;

import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private IUsersService usersService = new UsersServiceImpl();

    @Test
    @DisplayName("Assert getAllUsers return all users")
    void getAllUsers() {

        Users user1 = new Users("user1", "email@email.com", "password1");
        Users user2 = new Users("user2", "email@email.com", "password1");
        Users user3 = new Users("user3", "email@email.com", "password1");

        List<Users> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        Mockito.when(usersRepository.getAllUsers()).thenReturn(users);

        List<Users> mocked = usersService.getAllUsers();

        Assertions.assertSame(mocked, users);
        assertEquals(mocked.get(1), user2);
    }

    @Test
    void findUsersById() {
        Users user = new Users();
        user.setId(1);
        user.setEmail("email@email.com");
        user.setPassword("password");

        Mockito.when(usersRepository.findUsersById(1)).thenReturn(user);

        assertEquals(usersService.findUsersById(1), user);
    }

    @Test
    void findByEmail() {
        String email = "email1@email.com";
        Users user1 = new Users("user1", "email1@email.com", "password1");
        Users user2 = new Users("user2", "email2@email.com", "password2");

        Mockito.when(usersRepository.findByEmail(email)).thenReturn(user1);

        Users mocked = usersService.findByEmail(email);

        assertEquals("user1", mocked.getName());
        assertEquals("email1@email.com", mocked.getEmail());
        assertEquals("password1", mocked.getPassword());

    }
}