package com.katafrakt.airlinemanagement.controllers;

import com.katafrakt.airlinemanagement.models.requests.auth.AuthenticationRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.DeleteUserRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.PostCreateUserRequest;
import com.katafrakt.airlinemanagement.models.responses.auth.AuthenticationResponse;
import com.katafrakt.airlinemanagement.models.responses.auth.DeleteUserResponse;
import com.katafrakt.airlinemanagement.services.IAuthService;
import com.katafrakt.airlinemanagement.services.imp.AuthServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Authentication Controller", description = "Create User, token and manage credentials")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }
    @Operation(description = "Create a user without a role.")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody PostCreateUserRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(description = "Create a token with given user.")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws Exception{
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @Operation(description = "Delete given user with same user's token or admin token")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/delete")
    public DeleteUserResponse deleteUser(@RequestBody DeleteUserRequest request){
        return  authService.deleteUser(request);
    }
}