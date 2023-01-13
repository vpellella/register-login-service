package com.example.demo.conf;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@AllArgsConstructor
public class InMemoryUserConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnMissingBean(value = UserDetailsServiceImpl.class)
    public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder){

        UserDetails adminUser = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .authorities(new SimpleGrantedAuthority("ADMINISTRATOR"))
                .build();

        UserDetails basicUser = User.withUsername("user1")
                .password(passwordEncoder.encode("user1"))
                .roles("USER", "SIMPLE_USER")
                .authorities(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("SIMPLE_USER"))
                .build();
        return new InMemoryUserDetailsManager(adminUser, basicUser);
    }

}
