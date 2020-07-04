package com.example.apispringdemo.user.api.v1;

import com.example.apispringdemo.user.OnlineStatus;

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
}
