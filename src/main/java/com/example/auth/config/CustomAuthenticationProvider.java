package com.example.auth.config;

import com.example.auth.Model.AuthUser;
import com.example.auth.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    final PasswordEncoder passwordEncoder;

    final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        AuthUser userDetails = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
        if(passwordEncoder.matches(password, userDetails.getPassword())) {
            List<GrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(userDetails.getUserRole().toString()));
            UserDetails principal = new org.springframework.security.core.userdetails.User(
                    userDetails.getUsername(), userDetails.getPassword(), authorityList);
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorityList);
        } else {
            throw new BadCredentialsException("Invalid Password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
