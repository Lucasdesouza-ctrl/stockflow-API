package br.com.stockFlow.service;

import br.com.stockFlow.Model.User;
import br.com.stockFlow.enums.RolesEnum;
import br.com.stockFlow.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User user;

    @BeforeEach
    void setUp() {
         user = new User(
                UUID.randomUUID(),
                "TestName",
                "password",
                RolesEnum.ROLE_USER
        );
    }


    @Nested
    class createUser {


        @Test
        @DisplayName("Should create an user with success")
        void shouldCreateUser() {

            when(userRepository.save(user)).thenReturn(user);

            UUID output = userService.createUser(user);

            assertEquals(output, user.getId());


        }

        @Test
        @DisplayName("Should call repository save")
        void shouldCallRepositorySave() {

            when(userRepository.save(user))
                    .thenReturn(user);

            userService.createUser(user);

            verify(userRepository).save(user);
        }

        @Test
        @DisplayName("Should throw exception whern respository fails")
        void shouldThrowExceptionWhenRepositoryFails() {

            when(userRepository.save(any(User.class)))
                    .thenThrow(new RuntimeException("Create Error"));

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> userService.createUser(user)
            );

            assertEquals("Create Error", exception.getMessage());
        }
    }
}

