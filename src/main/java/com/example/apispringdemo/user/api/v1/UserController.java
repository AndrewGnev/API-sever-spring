package com.example.apispringdemo.user.api.v1;

import com.example.apispringdemo.user.IUserService;
import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @ApiOperation("add new user")
    @PostMapping(produces = "application/json")
    long post(
            @ApiParam("username for new user, must be unique")
            @RequestParam("username") String username,
            @ApiParam("email of new user, must be unique")
            @RequestParam("email") String email
    ) {
        try {
            return service.addUser(username, email);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @ApiOperation("get user by id")
    @GetMapping(path = "/{id}", produces = "application/json")
    UserDto get(
            @ApiParam("id of user")
            @PathVariable("id") long id
    ) {
        try {
            return entityToDto(service.getUser(id));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @ApiOperation("change user`s online status")
    @PutMapping(path = "/{id}", produces = "application/json")
    UserStatusDto put(
            @ApiParam("id of user")
            @PathVariable("id") long id,
            @ApiParam(allowableValues = "ONLINE, OFFLINE")
            @RequestParam("status") OnlineStatus status
    ) {
        try {
            return new UserStatusDto(id, service.setUserStatus(id, status), status);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private static UserDto entityToDto(UserEntity entity) {
        return new UserDto(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getStatus());
    }
}
