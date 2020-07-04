package com.example.apispringdemo.user.api.v1;

import com.example.apispringdemo.user.OnlineStatus;
import com.example.apispringdemo.user.UserEntity;
import com.example.apispringdemo.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    long post(
            @RequestParam("username") String username,
            @RequestParam("email") String email
    ) {
        try {
            return service.addUser(username, email);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    UserDto get(@PathVariable("id") long id) {
        try {
            return entityToDto(service.getUser(id));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    UserStatusDto put(
            @PathVariable("id") long id,
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
