package com.katafrakt.airlinemanagement.services;

import com.katafrakt.airlinemanagement.models.requests.auth.AuthenticationRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.DeleteUserRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.PostCreateUserRequest;
import com.katafrakt.airlinemanagement.models.responses.auth.AuthenticationResponse;
import com.katafrakt.airlinemanagement.models.responses.auth.DeleteUserResponse;

public interface IAuthService {
    DeleteUserResponse deleteUser(DeleteUserRequest user);

    AuthenticationResponse register(PostCreateUserRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;
}
