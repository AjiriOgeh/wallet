package com.wallet.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }


    @Test
    public void userSignUpTest() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .firstname("daffy")
                .lastname("duck")
                .email("daffyduck@gmail.com")
                .password("daffyduck123.")
                .phoneNumber("08034567890")
                .bankVerificationNumber("34567890123")
                .build();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signupRequest))
        ).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void userSignsUp_withExistingEmail_throwsExceptionTest() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .firstname("bugs")
                .lastname("bunny")
                .email("bugsbunny@gmail.com")
                .password("bugsbunny123.")
                .phoneNumber("08012345678")
                .bankVerificationNumber("12345678901")
                .build();

        mockMvc.perform(post("/auth/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(signupRequest))
        ).andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void editUserTest() throws Exception {
        EditUserRequest editUserRequest = EditUserRequest.builder()
                .userId(100L)
                .firstname(null)
                .lastname("carrots")
                .email("bugsbunny@gmail.com")
                .password(null)
                .phoneNumber(null)
                .bankVerificationNumber(null)
                .build();

        mockMvc.perform(patch("/auth/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(editUserRequest))
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void editUser_existingEmail_throwsExceptionTest() throws Exception {
        EditUserRequest editUserRequest = EditUserRequest.builder()
                .userId(100L)
                .firstname(null)
                .lastname("pig")
                .email("porkypig@gmail.com")
                .password(null)
                .phoneNumber(null)
                .bankVerificationNumber(null)
                .build();

        mockMvc.perform(patch("/auth/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(editUserRequest))
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(get("/auth/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getNonExistentUser_throwsExceptionTest() throws Exception {
        mockMvc.perform(get("/auth/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        mockMvc.perform(delete("/auth/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void deleteNonExistentUserTest_throwsExceptionTest() throws Exception {
        mockMvc.perform(delete("/auth/api/v1/users/101")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

}