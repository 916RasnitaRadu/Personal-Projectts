package com.exportApp.controller.userController;

import com.exportApp.mapper.AuthenticationResponse;
import com.exportApp.mapper.UserRequest;

import com.exportApp.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.authenticate(userRequest));
    }
}
