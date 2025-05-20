package com.katafrakt.airlinemanagement.services.imp;

import com.katafrakt.airlinemanagement.models.requests.auth.AuthenticationRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.DeleteUserRequest;
import com.katafrakt.airlinemanagement.models.requests.auth.PostCreateUserRequest;
import com.katafrakt.airlinemanagement.models.responses.auth.AuthenticationResponse;
import com.katafrakt.airlinemanagement.models.responses.auth.DeleteUserResponse;
import com.katafrakt.airlinemanagement.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceImp implements IAuthService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImp jwtService;

    @Override
    public DeleteUserResponse deleteUser(DeleteUserRequest user){
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))){
            jdbcUserDetailsManager.deleteUser(user.getUsername());
            return new DeleteUserResponse(user.getUsername());
        }
        else if (SecurityContextHolder.getContext().getAuthentication().getName().equals(user.getUsername())){
            jdbcUserDetailsManager.deleteUser(user.getUsername());
            return new DeleteUserResponse(user.getUsername());
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
    @Override
    public AuthenticationResponse register(PostCreateUserRequest request){
        UserDetails user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles("USER").build();
        jdbcUserDetailsManager.createUser(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, jwtService.extractExpiration(jwtToken));
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = jdbcUserDetailsManager.loadUserByUsername(request.getUsername());
        if(user != null){
            var token = jwtService.generateToken(user);
            return new AuthenticationResponse(token, jwtService.extractExpiration(token));
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
