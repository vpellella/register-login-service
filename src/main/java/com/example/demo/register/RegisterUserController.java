package com.example.demo.register;


import com.example.demo.common.enums.RoleEnum;
import com.example.demo.common.service.JwtService;
import com.example.demo.dto.UserRegistrationRequest;
import com.example.demo.dto.UserRegistrationResponse;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.AppUserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.common.utils.CommonUtils.getAuthoritiesFromRoles;

@RestController
@Slf4j
@AllArgsConstructor
public class RegisterUserController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserRegistrationResponse registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        System.out.println(request);
        log.info("Inside of register user");
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(getRoles(request));
        userRepository.save(user);

        var userDetails = new User(user.getUsername(), "dummy", getAuthoritiesFromRoles(user.getRoles()));

//        return "User "+ user.getUsername() +" registered successfully";
        return UserRegistrationResponse.builder()
                .message("User "+ user.getUsername() +" registered successfully")
                .token(jwtService.generateJwtToken(userDetails))
                .build();

    }

    private List<Role> getRoles(UserRegistrationRequest request) {
        List<Role> rolesList = request.getRoles()
                              .stream()
                              .map(role -> "ROLE_"+role.toUpperCase())
                              .map(role -> {
                                  return Role.builder()
                                             .name(RoleEnum.valueOf(role.toUpperCase()))
                                             .build();
                                      })
                              .toList();
        List<Role> authoritiesList = request.getAuthorities()
                                            .stream()
                                            .map(role -> {
                                                return Role.builder()
                                                        .name(RoleEnum.valueOf(role.toUpperCase()))
                                                        .build();
                                            })
                                            .toList();
        List<Role> roles = new ArrayList<>();
        roles.addAll(rolesList);
        roles.addAll(authoritiesList);
        return roles;
    }

}
