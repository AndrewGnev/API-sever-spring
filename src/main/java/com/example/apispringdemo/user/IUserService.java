package com.example.apispringdemo.user;

public interface IUserService {
    long addUser(String username, String email);
    UserEntity getUser(long id);
    OnlineStatus setUserStatus(long id, OnlineStatus status);
}
