package com.example.apispringdemo.user.api.v1;

public class AddUserResponseDto {

    private final Long id;
    private final String error;

    public AddUserResponseDto(long id) {
        this.id = id;
        this.error = null;
    }

    public AddUserResponseDto(String error) {
        this.id = null;
        this.error = error;
    }

    public Long getId() {
        return id;
    }

    public String getError() {
        return error;
    }
}
