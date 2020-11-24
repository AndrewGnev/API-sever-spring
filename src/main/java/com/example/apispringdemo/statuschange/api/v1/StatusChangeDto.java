package com.example.apispringdemo.statuschange.api.v1;

import com.example.apispringdemo.user.api.v1.UserDto;

import java.util.Objects;

public class StatusChangeDto {

    private final Long id;
    private final UserDto user;
    private final long timestamp;

    public StatusChangeDto(Long id, UserDto user, long timestamp) {
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public UserDto getUser() {
        return user;
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
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, timestamp);
    }
}
