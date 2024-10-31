package com.wallet.application.port.output;

import com.wallet.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserOutputPort {
    User save(User user);

    Optional<User> findById(Long id);

    void delete(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByBankVerificationNumber(String bankVerificationNumber);

    List<User> getAllUsers();
}
