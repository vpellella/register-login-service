package com.example.demo.register;


import com.example.demo.common.enums.RoleEnum;
import com.example.demo.dto.NewUserRegistrationRequest;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.Role;
import com.example.demo.repository.AppUserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class RegisterUserController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public RegisterUserController(AppUserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/users/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registerUser( @Valid @RequestBody NewUserRegistrationRequest request) {
        System.out.println(request);
        log.info("Inside of register user");
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(getRoles(request));
        userRepository.save(user);

        return "User "+ user.getUsername() +" registered successfully";

    }

    private List<Role> getRoles(NewUserRegistrationRequest request) {
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
