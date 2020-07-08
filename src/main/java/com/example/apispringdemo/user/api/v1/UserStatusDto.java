package com.example.apispringdemo.user.api.v1;

import com.example.apispringdemo.user.OnlineStatus;

import java.util.Objects;

public class UserStatusDto {

    private final long userId;
    private final OnlineStatus oldStatus;
    private final OnlineStatus newStatus;

    public UserStatusDto(long userId, OnlineStatus oldStatus, OnlineStatus newStatus) {
        this.userId = userId;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public long getUserId() {
        return userId;
    }

    public OnlineStatus getOldStatus() {
        return oldStatus;
    }

    public OnlineStatus getNewStatus() {
        return newStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatusDto that = (UserStatusDto) o;
        return userId == that.userId &&
                oldStatus == that.oldStatus &&
                newStatus == that.newStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, oldStatus, newStatus);
    }
}
