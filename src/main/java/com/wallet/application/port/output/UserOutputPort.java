package com.wallet.application.port.output;

import com.wallet.domain.model.User;

import java.util.Optional;

public interface UserOutputPort {
    User save(User user);

    Optional<User> findById(Long id);

    void delete(User user);
}
