package com.katafrakt.airlinemanagement.models.responses.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserResponse {
    @Getter @Setter
    private String username;
}
