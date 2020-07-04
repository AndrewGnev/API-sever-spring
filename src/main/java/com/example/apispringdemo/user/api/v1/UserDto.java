package com.example.apispringdemo.user.api.v1;

import com.example.apispringdemo.user.OnlineStatus;

public class UserDto {

    private final long id;
    private final String username;
    private final String email;
    private final OnlineStatus status;

    public UserDto(long id, String username, String email, OnlineStatus status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.status = status;
    }

    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public OnlineStatus getStatus() {
        return status;
    }
}
