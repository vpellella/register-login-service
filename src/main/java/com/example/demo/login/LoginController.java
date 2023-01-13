package com.example.demo.login;

import com.example.demo.common.service.JwtService;
import com.example.demo.repository.AppUserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.common.utils.CommonUtils.getAuthoritiesFromRoles;

@RestController
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginRequest.getUsername(), loginRequest.getPassword()));

        var appUser = appUserRepository.findByUsername(loginRequest.getUsername());
        var userDetails = new User(appUser.getUsername(), "dummy", getAuthoritiesFromRoles(appUser.getRoles()));

        return LoginResponse.builder()
                            .message("User details are valid")
                            .token(jwtService.generateJwtToken(userDetails))
                            .build();


    }
}
