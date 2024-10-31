package com.wallet.infrastructure.adapters.config;

import com.wallet.application.port.output.TransactionOutputPort;
import com.wallet.application.service.*;
import com.wallet.infrastructure.adapters.output.persistence.TransactionPersistenceAdapter;
import com.wallet.infrastructure.adapters.output.persistence.UserPersistenceAdapter;
import com.wallet.infrastructure.adapters.output.persistence.WalletPersistenceAdapter;
import com.wallet.infrastructure.adapters.output.persistence.mapper.TransactionPersistenceMapper;
import com.wallet.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import com.wallet.infrastructure.adapters.output.persistence.mapper.WalletPersistenceMapper;
import com.wallet.infrastructure.adapters.output.persistence.repository.PaystackOutputAdapter;
import com.wallet.infrastructure.adapters.output.persistence.repository.TransactionRepository;
import com.wallet.infrastructure.adapters.output.persistence.repository.UserRepository;
import com.wallet.infrastructure.adapters.output.persistence.repository.WalletRepository;
import org.keycloak.admin.client.Keycloak;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService(final UserPersistenceAdapter userPersistenceAdapter, final PasswordEncoder passwordEncoder, final WalletService walletService, final AuthService authService, final ValidationService validationService) {
        return new UserService(userPersistenceAdapter, passwordEncoder, walletService, authService, validationService);
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository, final UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }

    @Bean
    public WalletService walletService(final WalletPersistenceAdapter walletPersistenceAdapter, final PaystackService paystackService, final TransactionService transactionService) {
        return new WalletService(walletPersistenceAdapter, paystackService, transactionService);
    }

    @Bean
    public WalletPersistenceAdapter walletPersistenceAdapter(final WalletRepository walletRepository, final WalletPersistenceMapper walletPersistenceMapper){
        return new WalletPersistenceAdapter(walletRepository, walletPersistenceMapper);
    }

    @Bean
    public PaystackOutputAdapter paystackOutputAdapter() {
        return new PaystackOutputAdapter();
    }

    @Bean
    public TransactionService transactionService(final TransactionOutputPort transactionOutputPort) {
        return new TransactionService(transactionOutputPort);
    }

    @Bean
    public TransactionPersistenceAdapter transactionPersistenceAdapter(final TransactionRepository transactionRepository, final TransactionPersistenceMapper transactionPersistenceMapper) {
        return new TransactionPersistenceAdapter(transactionRepository, transactionPersistenceMapper);
    }

    @Bean
    public AuthService authService(final Keycloak keycloak, final WebClient webClient) {
        return new AuthService(keycloak, webClient);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ValidationService validationService(final WebClient webClient) {
        return new ValidationService(webClient);
    }

    @Bean
    public PaystackService paystackService(final WebClient webClient) {
        return new PaystackService(webClient);
    }
}
