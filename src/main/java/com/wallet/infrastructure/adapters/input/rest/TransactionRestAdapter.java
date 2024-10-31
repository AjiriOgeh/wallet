package com.wallet.infrastructure.adapters.input.rest;

import com.wallet.application.port.input.transactionServiceUseCases.GetAllTransactionsByDateUseCase;
import com.wallet.application.port.input.transactionServiceUseCases.GetAllTransactionsByStatusUseCase;
import com.wallet.application.port.input.transactionServiceUseCases.GetAllTransactionsUseCase;
import com.wallet.application.port.input.transactionServiceUseCases.GetTransactionByIdUseCase;
import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.TransactionStatus;
import com.wallet.infrastructure.adapters.input.rest.dto.response.ApiResponse;
import com.wallet.infrastructure.adapters.input.rest.mapper.TransactionRestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class TransactionRestAdapter {

    private final GetTransactionByIdUseCase getTransactionByIdUseCase;
    private final GetAllTransactionsUseCase getAllTransactionsUseCase;
    private final GetAllTransactionsByStatusUseCase getAllTransactionsByStatusUseCase;
    private final GetAllTransactionsByDateUseCase getAllTransactionsByDateUseCase;
    private final TransactionRestMapper transactionRestMapper;

    @GetMapping("/transactions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        Transaction transaction = getTransactionByIdUseCase.getTransactionById(id);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(transactionRestMapper
                        .mapTransactionToTransactionResponse(transaction), true));
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTransactions() {
        List<Transaction> transactions = getAllTransactionsUseCase.getAllTransactions();
        return ResponseEntity.status(OK)
                .body(new ApiResponse(transactions.stream()
                        .map(transactionRestMapper::mapTransactionToTransactionResponse).toList(), true));
    }

    @GetMapping("/transactions/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTransactionsByStatus(@RequestParam final TransactionStatus transactionStatus) {
        List<Transaction> transactions = getAllTransactionsByStatusUseCase.getAllTransactionsByStatus(transactionStatus);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(transactions.stream()
                        .map(transactionRestMapper::mapTransactionToTransactionResponse).toList(), true));
    }

    @GetMapping("/transactions/date")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTransactionsByDate(@RequestParam final LocalDateTime localDateTime) {
        List<Transaction> transactions = getAllTransactionsByDateUseCase.getAllTransactionsByDate(localDateTime);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(transactions.stream()
                        .map(transactionRestMapper::mapTransactionToTransactionResponse).toList(), true));
    }
}
