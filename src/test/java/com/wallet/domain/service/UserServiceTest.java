package com.wallet.domain.service;

import com.wallet.application.service.UserService;
import com.wallet.domain.exception.UserExistsException;
import com.wallet.domain.exception.UserNotFoundException;
import com.wallet.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/database/data.sql"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void userSignUpTest() {
        User user = User.builder()
                .firstname("daffy")
                .lastname("duck")
                .email("daffyduck@gmail.com")
                .password("daffyduck123.")
                .phoneNumber("08034567890")
                .bankVerificationNumber("34567890123")
                .build();
        User newUser = userService.signUp(user);

        assertThat(newUser).isNotNull();
        assertThat(newUser.getWallet()).isNotNull();
        assertEquals("daffyduck@gmail.com", newUser.getEmail());
    }

    @Test
    public void userSignsUp_withExistingEmail_throwsExceptionTest() {
        User user = User.builder()
                .firstname("bugs")
                .lastname("bunny")
                .email("bugsbunny@gmail.com")
                .password("bugsbunny123.")
                .phoneNumber("08012345678")
                .bankVerificationNumber("12345678901")
                .build();

        assertThrows(UserExistsException.class, ()-> userService.signUp(user));
    }

    @Test
    public void editUserTest() {
        User user = User.builder()
                .userId(100L)
                .firstname(null)
                .lastname("carrots")
                .email("bugssssbunny@gmail.com")
                .password(null)
                .phoneNumber(null)
                .bankVerificationNumber(null)
                .build();
        User editedUser = userService.updateUser(user);

        assertThat(editedUser).isNotNull();
        assertEquals("bugs", editedUser.getFirstname());
        assertEquals("carrots", editedUser.getLastname());
        assertEquals("bugsbunny@gmail.com", editedUser.getEmail());
        assertEquals("08012345678", editedUser.getPhoneNumber());
    }

    @Test
    public void editUser_existingEmail_throwsExceptionTest() {
        User user = User.builder()
                .userId(100L)
                .firstname(null)
                .lastname("pig")
                .email("porkypig@gmail.com")
                .password(null)
                .phoneNumber(null)
                .bankVerificationNumber(null)
                .build();

        assertThrows(UserExistsException.class, ()-> userService.updateUser(user));
    }

    @Test
    public void getUserByIdTest() {
        User user = userService.getUserById(100L);

        assertThat(user).isNotNull();
        assertEquals(100L, user.getUserId());
        assertEquals("bugsbunny@gmail.com", user.getEmail());
    }

    @Test
    public void getNonExistentUser_throwsExceptionTest() {
        assertThrows(UserNotFoundException.class, ()-> userService.getUserById(102L));
    }

    @Test
    public void deleteUserByIdTest() {
        userService.deleteUser(101L);

        try {
            userService.getUserById(101L);
        } catch (UserNotFoundException exception) {
            assertEquals("User with id 101 not found", exception.getMessage());
        }
    }

    @Test
    public void deleteNonExistentUserTest_throwsExceptionTest() {
        assertThrows(UserNotFoundException.class, ()-> userService.deleteUser(102L));
    }
}