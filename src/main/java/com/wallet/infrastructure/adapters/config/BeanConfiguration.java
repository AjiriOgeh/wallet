package com.wallet.infrastructure.adapters.config;

import com.wallet.domain.service.UserService;
import com.wallet.infrastructure.adapters.output.persistence.UserPersistenceAdapter;
import com.wallet.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import com.wallet.infrastructure.adapters.output.persistence.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService(final UserPersistenceAdapter userPersistenceAdapter) {
        return new UserService(userPersistenceAdapter);
    }

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(final UserRepository userRepository, final UserPersistenceMapper userPersistenceMapper) {
        return new UserPersistenceAdapter(userRepository, userPersistenceMapper);
    }
}
