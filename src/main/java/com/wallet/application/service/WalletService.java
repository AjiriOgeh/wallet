package com.wallet.application.service;

import com.wallet.application.port.input.walletServiceUseCases.DepositUseCase;
import com.wallet.application.port.input.walletServiceUseCases.*;
import com.wallet.application.port.output.PaymentGatewayOutputPort;
import com.wallet.application.port.output.WalletOutputPort;
import com.wallet.domain.exception.DepositRequestException;
import com.wallet.domain.exception.InvalidUserCredentialsException;
import com.wallet.domain.exception.WalletNotFoundException;
import com.wallet.domain.model.*;
import com.wallet.infrastructure.adapters.input.rest.dto.request.GetWalletTransactionsByDateRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitialisePaymentRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.wallet.domain.model.TransactionType.CREDIT;

@RequiredArgsConstructor
public class WalletService implements CreateWalletUseCase, GetWalletByIdUseCase,
        DepositUseCase, CheckBalanceUseCase, VerifyDepositUseCase,
        GetAllWalletTransactionsUseCase, GetAllWalletTransactionsByDateUseCase {

    private final WalletOutputPort walletOutputPort;
    private final PasswordEncoder passwordEncoder;
    private final PaymentGatewayOutputPort paymentGatewayOutputPort;
    private final TransactionService transactionService;

    @Lazy
    @Autowired
    private UserService userService;

    @Override
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        return walletOutputPort.save(wallet);
    }

    @Override
    public Wallet getWalletById(Long id) {
        return walletOutputPort.findById(id)
                .orElseThrow(()-> new WalletNotFoundException(
                        String.format("Wallet with id %d not found", id)));
    }

    @Override
    public InitialisePaymentResponse deposit(InitialisePaymentRequest initialisePaymentRequest) {
        User user = userService.getUserByEmail(initialisePaymentRequest.getEmail());
        if (!passwordEncoder.matches(initialisePaymentRequest.getPassword(), user.getPassword()))
            throw new InvalidUserCredentialsException("Invalid password");
        InitialisePaymentResponse response = paymentGatewayOutputPort.initialisePayment(
                initialisePaymentRequest.getEmail(), initialisePaymentRequest.getAmount()
                        .multiply(new BigDecimal(100)));
        if (!response.getMessage().equals("Authorization URL created")) {
            throw new DepositRequestException("Invalid deposit details");
        }
        System.out.println(response.getData().getAuthorizationUrl());
        System.out.println(response.getData().getReference());
        return response;
    }

    @Override
    public Transaction verifyDeposit(String reference) {
        VerifyPaymentResponse response = paymentGatewayOutputPort.verifyPayment(reference);
        User user = userService.getUserByEmail(response.getData().getCustomer().getEmail());
        Wallet wallet = user.getWallet();
        if (response.getData().getStatus().equals("success")) {
            BigDecimal amount = response.getData().getAmount();
            wallet.setBalance((wallet.getBalance().add(amount)).divide(new BigDecimal(100), RoundingMode.HALF_UP));
        }
        Transaction transaction = createTransaction(response);
        wallet.getTransactions().add(transaction);
        walletOutputPort.save(wallet);
        return transaction;
    }

    private Transaction createTransaction(VerifyPaymentResponse verifyPaymentResponse) {
        Transaction transaction = Transaction.builder()
                .amount(verifyPaymentResponse.getData().getAmount())
                .transactionType(CREDIT)
                .transactionStatus(TransactionStatus.valueOf(verifyPaymentResponse
                        .getData().getStatus().toUpperCase()))
                .build();
        return transactionService.createTransaction(transaction);
    }

    @Override
    public Wallet checkBalance(Long id) {
        return getWalletById(id);
    }

    @Override
    public List<Transaction> getAllWalletTransactions(Long id) {
        Wallet wallet = getWalletById(id);
        return wallet.getTransactions();
    }

    @Override
    public List<Transaction> getAllWalletTransactionsByDate(
            GetWalletTransactionsByDateRequest getWalletTransactionsByDateRequest) {
        List<Transaction> transactions = new ArrayList<>();
        Wallet wallet = getWalletById(getWalletTransactionsByDateRequest.getId());
        for (Transaction transaction : wallet.getTransactions()){
            if (transaction.getDate().isEqual(getWalletTransactionsByDateRequest.getDate())) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}