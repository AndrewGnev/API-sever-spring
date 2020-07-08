package com.example.apispringdemo.statuschange.api.v1;

import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;

import java.util.Objects;

public class StatusChangeDto {

    private final Long id;
    private final UserEntity user;
    private final OnlineStatus status;
    private final long timestamp;

    public StatusChangeDto(Long id, UserEntity user, OnlineStatus status, long timestamp) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public OnlineStatus getStatus() {
        return status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusChangeDto that = (StatusChangeDto) o;
        return timestamp == that.timestamp &&
                Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, timestamp);
    }
}
