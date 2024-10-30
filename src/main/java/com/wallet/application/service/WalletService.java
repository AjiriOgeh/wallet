package com.wallet.application.service;

import com.wallet.application.port.input.walletServiceUseCases.DepositUseCase;
import com.wallet.application.port.input.walletServiceUseCases.*;
import com.wallet.application.port.output.WalletOutputPort;
import com.wallet.domain.exception.WalletNotFoundException;
import com.wallet.domain.model.*;
import com.wallet.infrastructure.adapters.input.rest.dto.request.InitialisePaymentRequest;
import com.wallet.infrastructure.adapters.input.rest.dto.response.InitialisePaymentResponse;
import com.wallet.infrastructure.adapters.input.rest.dto.response.VerifyPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.wallet.domain.model.TransactionType.CREDIT;

@RequiredArgsConstructor
public class WalletService implements CreateWalletUseCase, GetWalletByIdUseCase,
        DepositUseCase, CheckBalanceUseCase, VerifyDepositUseCase,
        GetAllWalletTransactionsUseCase, GetAllWalletTransactionsByDateUseCase {

    private final WalletOutputPort walletOutputPort;
    private final PaystackService paystackService;
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
        userService.getUserByEmail(initialisePaymentRequest.getEmail());
        InitialisePaymentResponse response = paystackService.initialisePayment(
                initialisePaymentRequest.getEmail(), initialisePaymentRequest.getAmount());

        if (!response.getMessage().equals("Authorization URL created")) {
            throw new RuntimeException("jkjjlkjjlkj");
        }
        return response;
    }

    @Override
    public Transaction verifyDeposit(String reference) {
        VerifyPaymentResponse response = paystackService.verifyPayment(reference);
        if(!response.getData().getStatus().equals("success")) {
            throw new RuntimeException("");
        }
        BigDecimal amount = response.getData().getAmount();
        User user = userService.getUserByEmail(response.getData().getCustomer().getEmail());
        Wallet wallet = getWalletById(user.getWallet().getWalletId());
        wallet.setBalance(wallet.getBalance().add(amount));
        Transaction transaction = createTransaction(response, wallet);
        wallet.getTransactions().add(transaction);
        walletOutputPort.save(wallet);
        return transaction;
    }

    private Transaction createTransaction(VerifyPaymentResponse verifyPaymentResponse, Wallet wallet) {
        Transaction transaction = Transaction.builder()
                .amount(verifyPaymentResponse.getData().getAmount())
                .transactionType(CREDIT)
//                .wallet(wallet)
                .build();
        return transactionService.createTransaction(transaction);
    }

    @Override
    public BigDecimal checkBalance(Long id) {
        Wallet wallet = getWalletById(id);
        return wallet.getBalance();
    }

    @Override
    public List<Transaction> getAllWalletTransactions(Long id) {
        Wallet wallet = getWalletById(id);
        return wallet.getTransactions();
    }

    @Override
    public List<Transaction> getAllWalletTransactionsByDate(GetAllWalletTransactionsByDate getAllWalletTransactionsByDate) {
        List<Transaction> transactions = new ArrayList<>();
        Wallet wallet = getWalletById(getAllWalletTransactionsByDate.getId());
        System.out.println(wallet.getTransactions());
        for (Transaction transaction : wallet.getTransactions()){
            if (transaction.getDate().isEqual(getAllWalletTransactionsByDate.getDate())) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    // getall waletts,
    // transfer

    public static void main(String[] args) {
        TransactionType transactionType = TransactionType.valueOf("DEBIT");
        System.out.println(transactionType);
    }
}
