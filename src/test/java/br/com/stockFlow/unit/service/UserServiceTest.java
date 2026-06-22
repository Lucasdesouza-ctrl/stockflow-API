package br.com.stockFlow.unit.service;

import br.com.stockFlow.Model.User;
import br.com.stockFlow.enums.Constants;
import br.com.stockFlow.enums.RolesEnum;
import br.com.stockFlow.exception.NotFoundException;
import br.com.stockFlow.repository.UserRepository;
import br.com.stockFlow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
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
            when(userRepository.save(any(User.class)))
                    .thenAnswer(i -> i.getArgument(0));
            UUID result = userService.createUser(user);

            assertEquals(result, user.getId());
            verify(userRepository, times(1)).save(user);
        }

        @Test
        @DisplayName("Should throw exception when respository fails")
        void shouldThrowExceptionWhenRepositoryFails() {
            when(userRepository.existsByName(user.getName()))
                    .thenReturn(true);

            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> userService.createUser(user)
            );

            assertEquals("Username already exists", exception.getMessage());
            verify(userRepository, times(1)).existsByName(user.getName());
        }

        @Test
        @DisplayName("Should throw an Exception when username already exists")
        void shouldThrowExceptionWhenUsernameAlreadyExists() {
            when(userRepository.existsByName(user.getName()))
                    .thenReturn(true);

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> userService.createUser(user));

            assertEquals("Username already exists", exception.getMessage());
            verify(userRepository, times(1)).existsByName(user.getName());
        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should find user by Id with success")
        void shouldFindUserWithSuccess() {
            when(userRepository.findById(any(UUID.class)))
                    .thenReturn(Optional.ofNullable(user));

            User result = userService.getUserById(user.getId());

            assertEquals(user, result);
            verify(userRepository, times(1)).findById(user.getId());
        }

        @Test
        @DisplayName("Should throw exception when user not found")
        void shouldThrowExceptionWhenUserNotFound() {

            when(userRepository.findById(user.getId()))
                    .thenReturn(Optional.empty());

            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userService.getUserById(user.getId()));

            assertEquals(Constants.MSG_NOT_FOUND.getValue(), exception.getMessage());
            verify(userRepository, times(1)).findById(user.getId());
        }
    }

    @Nested
    class DeleteById {

        @DisplayName("Should delete with success")
        @Test
        void shouldDeleteWithSuccess() {
            when(userRepository.existsById(user.getId())).thenReturn(true);

            userService.deleteUserById(user.getId());

            verify(userRepository, times(1)).existsById(user.getId());
            verify(userRepository, times(1)).deleteById(user.getId());
        }

        @DisplayName("Should throws an exception when user not found")
        @Test
        void shouldThrowExceptionWhenUserNotFound() {

            when(userRepository.existsById(user.getId())).thenReturn(false);

            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> userService.deleteUserById(user.getId()));

            assertEquals(Constants.MSG_NOT_FOUND.getValue(), exception.getMessage());
            verify(userRepository, times(1)).existsById(user.getId());
        }
    }

    @Nested
    class listAllUsers {

        @DisplayName("Should return all users with success")
        @Test
        void shouldReturnAllUsersWithSuccess() {
            when(userRepository.findAll()).thenReturn(List.of(user));

            List<User> userList = userService.getAllUsers();

            assertNotNull(userList);
            verify(userRepository, times(1)).findAll();
        }

    }
}

