package com.Leo.first_spring_crud.service;

import com.Leo.first_spring_crud.controller.Dto.CreateUserDto;
import com.Leo.first_spring_crud.controller.Dto.UpdateUserDto;
import com.Leo.first_spring_crud.entity.User;
import com.Leo.first_spring_crud.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {

            //Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "name",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = new CreateUserDto(
                    "name",
                    "email@email",
                    "123"
            );

            //Act
            var output = userService.createUser(input);

            //Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.name(), userCaptured.getName());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());

        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            //Arrange

            var user = new User(
                    UUID.randomUUID(),
                    "name",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            doThrow(new RuntimeException()).when(userRepository).save(any());

            var input = new CreateUserDto(
                    "name",
                    "email@email",
                    "123"
            );

            //Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUserById {

        @DisplayName("Should get user by id with success when optional is present")
        @Test
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "name",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(user.getId().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());

        }

        @DisplayName("Should get user by id with success when optional is empty")
        @Test
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {

            //Arrange
            var userId = UUID.randomUUID();


            doReturn(Optional.empty()).
                    when(userRepository).
                    findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(userId.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());

        }

    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "name",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            var userList = List.of(user);

            doReturn(List.of(user))
                    .when(userRepository)
                    .findAll();

            //Act
            var output = userService.listUsers();

            //Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists(){

            //Arrange
            doReturn(true)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());

            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            //Act
            userService.deleteUserById(userId.toString());

            //Assert
            var idList = uuidArgumentCaptor.getAllValues();

            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository,times(1)).existsById(idList.get(0));
            verify(userRepository,times(1)).deleteById(idList.get(1));
        }
        @Test
        @DisplayName("Should not delete user with success when user not exists")
        void shouldNotDeleteUserWithSuccessWhenUserNotExists(){

            //Arrange
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();

            //Act
            userService.deleteUserById(userId.toString());

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository,times(1))
                    .existsById(uuidArgumentCaptor.getValue());
            verify(userRepository,times(0))
                    .deleteById(any());
        }
    }

    @Nested
    class updateById{

        @Test
        @DisplayName("Should update user by id when user exists and name and passwords is filled")
        void shouldUpdateUserByIdWhenUserExistsAndNameAndPasswordIsFilled() {

            //Arrange

            var updateUserDto = new UpdateUserDto(
                    "newName",
                    "newPassword"
            );
            var user = new User(
                    UUID.randomUUID(),
                    "name",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());
            doReturn(user)
                    .when(userRepository)
                    .save(userArgumentCaptor.capture());

            //Act
            userService.updateUserById(user.getId().toString(), updateUserDto);

            //Assert
            assertEquals(user.getId(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDto.name(), userCaptured.getName());
            assertEquals(updateUserDto.password(), userCaptured.getPassword());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1))
                    .save(user);
        }

        @Test
        @DisplayName("Should not update user by id when user not exists")
        void shouldNotUpdateUserByIdWhenUserNotExistsAndNameAndPasswordIsFilled() {

            //Arrange

            var updateUserDto = new UpdateUserDto(
                    "newName",
                    "newPassword"
            );
            var userId = UUID.randomUUID();

            doReturn(Optional.empty())
                    .when(userRepository)
                    .findById(uuidArgumentCaptor.capture());

            //Act
            userService.updateUserById(userId.toString(), updateUserDto);

            //Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0))
                    .save(any() );
        }
    }
}
