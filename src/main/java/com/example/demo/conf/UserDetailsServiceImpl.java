package com.example.demo.conf;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.example.demo.common.utils.CommonUtils.getAuthoritiesFromRoles;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository userRepository;
    public UserDetailsServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Total Users:: " +this.userRepository.count());
        System.out.println("username: " + username);
        var appUser = this.userRepository.findByUsername(username);
        return new User(appUser.getUsername(), appUser.getPassword(), getAuthorities(appUser));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(AppUser user) {
        return getAuthoritiesFromRoles(user.getRoles());
    }

}
