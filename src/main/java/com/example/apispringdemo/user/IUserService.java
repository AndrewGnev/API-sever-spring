package com.example.apispringdemo.user;

import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    UserEntity getUser(long id);
    long addUser(String username, String email);
    OnlineStatus setUserStatus(long id, OnlineStatus status);
}
