//package com.wallet.infrastructure.adapters.input.rest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.wallet.domain.model.InitializePaymentRequestchanged;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class WalletRestAdapterTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    public void makeNewDeposit() throws Exception {
////        InitializePaymentRequest initializePaymentRequest = InitializePaymentRequest.builder()
////                .email("bugsbunny@gmail.com")
////                .amount(new BigDecimal(5000))
////                .build();
////        mockMvc.perform(post("/deposit")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsBytes(initializePaymentRequest))
////        ).andExpect(status().isOk()).andDo(print());
//
////        InitializePaymentRequest initializePaymentRequest = InitializePaymentRequest.builder()
////                .email("bugsbunny@gmail.com")
////                .amount(new BigDecimal(500000))  // Adjust amount to kobo/cents
////                .build();
////
////        mockMvc.perform(post("/deposit")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(objectMapper.writeValueAsBytes(initializePaymentRequest))
////        ).andExpect(status().isOk()).andDo(print());
//
//        InitializePaymentRequestchanged initializePaymentRequestchanged = InitializePaymentRequestchanged.builder()
//                .email("ajiogeh@yahoo@gmail.com")
//                .amount(new BigDecimal("500000"))  // Ensure amount is not null
//                .build();
//
//        mockMvc.perform(post("/deposit")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(initializePaymentRequestchanged))
//        ).andExpect(status().isOk()).andDo(print());
//    }
//}