package com.wallet.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.application.service.UserService;
import com.wallet.domain.model.AuthToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/database/data.sql"})
public class TransactionRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private AuthToken token;

    @BeforeEach
    public void setUp() {
        token = userService.login("tedjacob@gmail.com", "password");
    }

    @Test
    public void getTransactionById() throws Exception{
        mockMvc.perform(get("/api/v1/transactions/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getAllTransactions() throws Exception{
        mockMvc.perform(get("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getAllTransactionsByStatus() throws Exception{
        mockMvc.perform(get("/api/v1/transactions/status?transactionStatus=SUCCESS")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void getAllTransactionsByDate() throws Exception{
        mockMvc.perform(get("/api/v1/transactions/date?localDateTime=2024-10-20T10:41:55.377819600")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }
}