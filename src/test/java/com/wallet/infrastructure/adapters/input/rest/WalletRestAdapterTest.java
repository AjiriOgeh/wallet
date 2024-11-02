package com.wallet.infrastructure.adapters.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitialisePaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletRestAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }


    @Test
    public void deposit() throws Exception{
        InitialisePaymentRequest initialisePaymentRequest = InitialisePaymentRequest.builder()
                .email("alexhunt@gmail.com")
                .amount(new BigDecimal(50_000))
                .build();

        mockMvc.perform(post("/wallet/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(initialisePaymentRequest))
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void verify()throws Exception {
        mockMvc.perform(get("/wallet/verify/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getWallet() throws Exception{
        mockMvc.perform(get("/wallet/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getBalance() throws Exception{
        mockMvc.perform(get("/wallet/balance/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void getAllWalletTransactions() throws Exception{
        mockMvc.perform(get("/wallet/transactions")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(print());
    }
}