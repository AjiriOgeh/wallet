package com.wallet.infrastructure.adapters.output.persistence.repository;

import com.wallet.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("Select u from UserEntity u where u.email=:email")
    Optional<UserEntity> findByEmail(String email);
}
