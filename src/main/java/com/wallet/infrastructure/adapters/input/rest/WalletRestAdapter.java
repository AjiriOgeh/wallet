package com.wallet.infrastructure.adapters.input.rest;

import com.wallet.application.port.input.walletServiceUseCases.*;
import com.wallet.infrastructure.adapters.input.rest.dto.request.GetWalletTransactionsByDateRequest;
import com.wallet.domain.model.Transaction;
import com.wallet.domain.model.Wallet;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitialisePaymentRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.ApiResponse;
import com.wallet.infrastructure.adapters.input.rest.mapper.WalletRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RequestMapping("/api/v1/wallet")
@Validated
@RestController
@RequiredArgsConstructor
public class WalletRestAdapter {

    private final DepositUseCase depositUseCase;
    private final VerifyDepositUseCase verifyDepositUseCase;
    private final GetWalletByIdUseCase getWalletByIdUseCase;
    private final CheckBalanceUseCase checkBalanceUseCase;
    private final GetAllWalletTransactionsUseCase getAllWalletTransactionsUseCase;
    private final GetAllWalletTransactionsByDateUseCase getAllWalletTransactionsByDateUseCase;
    private final WalletRestMapper walletRestMapper;

    @PostMapping("/deposit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deposit(@RequestBody @Valid final InitialisePaymentRequest initialisePaymentRequest) {
        return ResponseEntity.status(OK)
                .body(new ApiResponse(depositUseCase.deposit(initialisePaymentRequest), true));
    }

    @GetMapping("/verify/{reference}")
    public ResponseEntity<?> verify(@PathVariable final String reference) {
        return ResponseEntity.status(OK)
                .body(new ApiResponse(verifyDepositUseCase.verifyDeposit(reference), true));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getWallet(@PathVariable final Long id) {
        Wallet wallet = getWalletByIdUseCase.getWalletById(id);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(walletRestMapper.mapWalletToGetWalletResponse(wallet), true));
    }

    @GetMapping("/balance/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getBalance(@PathVariable final Long id) {
        Wallet wallet = checkBalanceUseCase.checkBalance(id);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(walletRestMapper.mapWalletToGetBalanceResponse(wallet), true));
    }

    @GetMapping("/transactions/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getAllWalletTransactions(@PathVariable final Long id) {
        List<Transaction> allWalletTransactions = getAllWalletTransactionsUseCase.getAllWalletTransactions(id);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(allWalletTransactions.stream()
                        .map(walletRestMapper::mapTransactionToTransactionResponse).toList(), true));
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getAllWalletTransactionsByDate(
            @RequestBody @Valid GetWalletTransactionsByDateRequest getWalletTransactionsByDateRequest) {
        List<Transaction> transactions = getAllWalletTransactionsByDateUseCase.
                getAllWalletTransactionsByDate(getWalletTransactionsByDateRequest);
        return ResponseEntity.status(OK)
                .body(new ApiResponse(transactions.stream()
                        .map(walletRestMapper::mapTransactionToTransactionResponse).toList(), true));
    }
}
