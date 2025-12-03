package com.example.ecommerce.auth_service.adapter.out;

import com.example.ecommerce.auth_service.adapter.out.mapper.UserEntityMapper;
import com.example.ecommerce.auth_service.domain.entity.User;
import com.example.ecommerce.auth_service.domain.port.out.UserRepositoryPort;
import com.example.ecommerce.auth_service.infrastructure.persistence.entity.UserEntity;
import com.example.ecommerce.auth_service.infrastructure.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper = UserEntityMapper.INSTANCE;

    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity =  userRepository.findByEmail(email);
        return userEntity.map(userEntityMapper::toDomain);

    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userEntityMapper.toEntity(user);
        UserEntity savedEntity = userRepository.save(userEntity);
        return userEntityMapper.toDomain(savedEntity);
    }

    @Override
    public boolean findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password )

    }
}
