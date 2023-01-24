package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {

    @NotNull
    @Size(min = 6, max = 25)
    private String username;

    @NotNull
    @Pattern(regexp = "[A-Z]+[A-Za-z0-9]*")
    private String password;

    private List<String> roles = new ArrayList<>();

    private List<String> authorities;

}
