package com.wallet.domain.service;

import com.wallet.application.port.output.UserOutputPort;
import com.wallet.domain.model.User;
import com.wallet.domain.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserOutputPort userOutputPort;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .userId(1L)
                .firstname("jon")
                .lastname("snow")
                .email("jonsnow@gmail.com")
                .password("password123.")
                .phoneNumber("09012345678")
                .bankVerificationNumber("12345678901")
                .wallet(new Wallet())
                .build();
    }

    @Test
    public void userSignUpTest() {
        when(userOutputPort.save(user)).thenReturn(user);
        User newUser = userService.signUp(user);

        assertEquals(user, newUser);
        verify(userOutputPort).save(user);
    }

    @Test
    public void getUserById() {
        when(userOutputPort.findById(1L)).thenReturn(Optional.of(user));
        User existingUser = userService.getUserById(1L);

        assertThat(existingUser).isNotNull();
        assertEquals(existingUser.getUserId(), 1L);
        assertEquals(existingUser.getFirstname(), "jon");
    }
}