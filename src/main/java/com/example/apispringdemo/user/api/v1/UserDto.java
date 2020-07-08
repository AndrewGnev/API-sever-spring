package com.example.apispringdemo.user.api.v1;

import com.example.apispringdemo.user.OnlineStatus;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
                Objects.equals(username, userDto.username) &&
                Objects.equals(email, userDto.email) &&
                status == userDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, status);
    }
}
