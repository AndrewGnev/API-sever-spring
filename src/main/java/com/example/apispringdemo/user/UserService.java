package com.example.apispringdemo.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public long addUser(String username, String email) {
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("username is already busy");
        }

        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("email is already busy");
        }

        return repository.saveAndFlush(new UserEntity(username, email)).getId();
    }
}
