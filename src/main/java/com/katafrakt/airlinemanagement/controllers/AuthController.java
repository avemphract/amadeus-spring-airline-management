package com.katafrakt.airlinemanagement.controllers;

import com.katafrakt.airlinemanagement.models.requests.auth.AuthenticationRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.DeleteUserRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.PostCreateUserRequest;
import com.katafrakt.airlinemanagement.models.responses.auth.AuthenticationResponse;
import com.katafrakt.airlinemanagement.models.responses.auth.DeleteUserResponse;
import com.katafrakt.airlinemanagement.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Authentication Controller", description = "Create User, token and manage credentials")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody PostCreateUserRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception{
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/delete")
    public DeleteUserResponse deleteUser(@RequestBody DeleteUserRequest request){
        return  authService.deleteUser(request);
    }
}