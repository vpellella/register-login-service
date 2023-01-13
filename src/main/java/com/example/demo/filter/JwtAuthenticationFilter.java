package com.example.demo.filter;

import com.example.demo.common.service.JwtService;
import com.example.demo.repository.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.demo.common.utils.CommonUtils.getAuthoritiesFromRoles;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final JwtService jwtService;
    public final AppUserRepository appUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken != null && jwtService.validateToken(jwtToken)){
            jwtToken = jwtToken.substring("Bearer ".length());
            String username = jwtService.getUsername(jwtToken);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                var appUser = appUserRepository.findByUsername(username);
                var userDetails = new User(appUser.getUsername(), appUser.getPassword(), getAuthoritiesFromRoles(appUser.getRoles()));
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                null,
                                userDetails.getAuthorities()));
            }
        }

        filterChain.doFilter(request, response);

    }
}
