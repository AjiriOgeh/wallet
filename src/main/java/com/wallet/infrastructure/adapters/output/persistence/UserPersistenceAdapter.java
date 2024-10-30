package com.wallet.infrastructure.adapters.output.persistence;

import com.wallet.application.port.output.UserOutputPort;
import com.wallet.domain.model.User;
import com.wallet.infrastructure.adapters.output.persistence.entity.UserEntity;
import com.wallet.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import com.wallet.infrastructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserOutputPort {

    private final UserRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;
    @Override
    public User save(final User user) {
        UserEntity userEntity = userPersistenceMapper.mapUserToUserEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userPersistenceMapper.mapUserEntityToUser(savedUserEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isEmpty()) return Optional.empty();
        User user = userPersistenceMapper.mapUserEntityToUser(userEntity.get());
        return Optional.of(user);
    }

    @Override
    public void delete(User user) {
        UserEntity userEntity = userPersistenceMapper.mapUserToUserEntity(user);
        userRepository.delete(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity =  userRepository.findByEmail(email);
        if(userEntity.isEmpty()) return Optional.empty();
        User user = userPersistenceMapper.mapUserEntityToUser(userEntity.get());
        return Optional.of(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream().map(userPersistenceMapper::mapUserEntityToUser).toList();

    }
}
