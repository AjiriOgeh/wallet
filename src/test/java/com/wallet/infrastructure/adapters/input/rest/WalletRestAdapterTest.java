package com.wallet.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.application.service.UserService;
import com.wallet.domain.model.AuthToken;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitialisePaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/database/data.sql"})
public class WalletRestAdapterTest {

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
    public void deposit() throws Exception{
        InitialisePaymentRequest initialisePaymentRequest = InitialisePaymentRequest.builder()
                .email("alexhunt@gmail.com")
                .amount(new BigDecimal(50000))
                .password("password")
                .build();

        mockMvc.perform(post("/api/v1/wallet/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(initialisePaymentRequest))
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    public void verify()throws Exception {
        mockMvc.perform(get("/api/v1/wallet/verify/564xl4u5a2")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getWallet() throws Exception{
        mockMvc.perform(get("/api/v1/wallet/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getBalance() throws Exception{
        mockMvc.perform(get("/api/v1/wallet/balance/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getAllWalletTransactions() throws Exception{
        mockMvc.perform(get("/api/v1/wallet/transactions/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token.getAccessToken())
        ).andExpect(status().isOk()).andDo(print());
    }
}