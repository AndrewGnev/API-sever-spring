package com.example.apispringdemo.user.api.v1;

import com.example.apispringdemo.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<AddUserResponseDto> post(
            @RequestParam("username") String username,
            @RequestParam("email") String email
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AddUserResponseDto(service.addUser(username, email)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AddUserResponseDto(e.getMessage()));
        }
    }
}
