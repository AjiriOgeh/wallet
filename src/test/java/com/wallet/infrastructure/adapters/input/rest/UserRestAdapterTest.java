package com.wallet.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.application.service.UserService;
import com.wallet.domain.model.AuthToken;
import com.wallet.infrastructure.adapters.input.rest.dto.request.EditUserRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/database/data.sql"})
public class UserRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private AuthToken token;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        token = userService.login("alexhunt@gmail.com", "password");
    }


    @Test
    public void userSignUpTest() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .firstname("jason")
                .lastname("robin")
                .email("jasonrobin@gmail.com")
                .password("password123.")
                .phoneNumber("08067899822")
                .bankVerificationNumber("23234545111")
                .build();

        mockMvc.perform(post("/api/v1/users/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signupRequest))
        ).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void userSignsUp_withExistingEmail_throwsExceptionTest() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .firstname("alex")
                .lastname("hunter")
                .email("alexhunt@gmail.com")
                .password("password123.")
                .phoneNumber("08066778899")
                .bankVerificationNumber("54321543210")
                .build();

        mockMvc.perform(post("/api/v1/users/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signupRequest))
        ).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void editUserTest() throws Exception {
        EditUserRequest editUserRequest = EditUserRequest.builder()
                .userId(100L)
                .lastname("parker")
                .email("james-parker@gmail.com")
                .build();

        mockMvc.perform(patch("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(editUserRequest))
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void editUser_existingEmail_throwsExceptionTest() throws Exception {
        EditUserRequest editUserRequest = EditUserRequest.builder()
                .userId(100L)
                .lastname("hunter")
                .email("alexhunt@gmail.com")
                .build();

        mockMvc.perform(patch("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(editUserRequest))
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(get("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getNonExistentUser_throwsExceptionTest() throws Exception {
        mockMvc.perform(get("/api/v1/users/34567")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void deleteNonExistentUserTest_throwsExceptionTest() throws Exception {
        mockMvc.perform(delete("/api/v1/users/34567")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isNotFound()).andDo(print());
    }

}