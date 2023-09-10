package com.katafrakt.airlinemanagement.models.responses.auth;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    @Getter @Setter
    private String token;

    @Getter @Setter
    private Date expiresIn;
}
