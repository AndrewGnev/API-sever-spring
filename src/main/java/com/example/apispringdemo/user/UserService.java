package com.example.apispringdemo.user;

import com.example.apispringdemo.statuschange.IStatusChangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository repository;

    private final IStatusChangeService statusChangeService;

    public UserService(UserRepository repository, IStatusChangeService statusChangeService) {
        this.repository = repository;
        this.statusChangeService = statusChangeService;
    }

    public long addUser(String username, String email) {
        if (repository.existsByUsername(username)) {
            throw new IllegalArgumentException("username is already busy");
        }

        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("email is already busy");
        }

        final UserEntity user = repository.saveAndFlush(new UserEntity(username, email));
        statusChangeService.addStatusChange(user, OnlineStatus.OFFLINE, System.currentTimeMillis());
        return user.getId();
    }

    public UserEntity getUser(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("user doesn't exist");
        }

        return repository.findById(id);
    }

    public OnlineStatus setUserStatus(long id, OnlineStatus status) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("user doesn't exist");
        }

        final UserEntity user = repository.getOne(id);
        final OnlineStatus oldStatus = user.getStatus();
        user.setStatus(status);

        statusChangeService.addStatusChange(user, status, System.currentTimeMillis());
        return oldStatus;
    }
}
