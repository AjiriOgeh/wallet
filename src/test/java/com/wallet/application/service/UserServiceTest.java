package com.wallet.application.service;

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
                .firstname("max")
                .lastname("young")
                .email("maxyoung@gmail.com")
                .password("password123.")
                .phoneNumber("08078901234")
                .bankVerificationNumber("78901234507")
                .build();
        User newUser = userService.signUp(user);

        assertThat(newUser).isNotNull();
        assertThat(newUser.getWallet()).isNotNull();
        assertEquals("maxyoung@gmail.com", newUser.getEmail());
    }

    @Test
    public void userSignsUp_withExistingEmail_throwsExceptionTest() {
        User user = User.builder()
                .firstname("alexander")
                .lastname("hunter")
                .email("alexhunt@gmail.com")
                .password("password123.")
                .phoneNumber("08089012345")
                .bankVerificationNumber("12345678901")
                .build();

        assertThrows(UserExistsException.class, ()-> userService.signUp(user));
    }

    @Test
    public void editUserTest() {
        User user = User.builder()
                .userId(100L)
                .firstname(null)
                .lastname("hunt-parker")
                .email("alexhunt@gmail.com")
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
                .lastname("hunt-parker")
                .email("alexhunt@gmail.com")
                .build();

        assertThrows(UserExistsException.class, ()-> userService.updateUser(user));
    }

    @Test
    public void getUserByIdTest() {
        User user = userService.getUserById(100L);

        assertThat(user).isNotNull();
        assertEquals(100L, user.getUserId());
        assertEquals("alex", user.getFirstname());
        assertEquals("alexhunt@gmail.com", user.getEmail());
    }

    @Test
    public void getNonExistentUser_throwsExceptionTest() {
        assertThrows(UserNotFoundException.class, ()-> userService.getUserById(34567L));
    }

    @Test
    public void deleteUserByIdTest() {
        userService.deleteUser(104L);
        try {
            userService.getUserById(104L);
        } catch (UserNotFoundException exception) {
            assertEquals("User with id 104 not found", exception.getMessage());
        }
    }

    @Test
    public void deleteNonExistentUserTest_throwsExceptionTest() {
        assertThrows(UserNotFoundException.class, ()-> userService.deleteUser(34567L));
    }
}