package com.example.auth.Services;

import com.example.auth.DTO.UserRegistrationRequest;
import com.example.auth.DTO.UserSignInRequest;
import com.example.auth.Model.AuthUser;
import com.example.auth.Repository.UserRepository;
import com.example.auth.config.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final ModelMapper modelMapper;

    final CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                    ModelMapper modelMapper, CustomAuthenticationProvider customAuthenticationProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


    public void registerUser(UserRegistrationRequest userRegistrationRequest) {
        AuthUser authUser = modelMapper.map(userRegistrationRequest, AuthUser.class);
        String encodedPassword = passwordEncoder.encode(authUser.getPassword());
        authUser.setPassword(encodedPassword);
        userRepository.save(authUser);
    }

    public String signIn(UserSignInRequest userSignInRequest) {
        try {
            if(checkLoggedInStatus(userSignInRequest)) {
                return "Hello" + ", you're already logged in";
            }
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userSignInRequest.getUsername(), userSignInRequest.getPassword());
            Authentication authenticated = customAuthenticationProvider.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            return "user is signed in successfully";
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public boolean checkLoggedInStatus(UserSignInRequest userSignInRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof UserDetails && authentication.getName().equals(userSignInRequest.getUsername())) {
            String username = authentication.getName();
            return true;
        } else return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Create and return the UserDetails object based on your AuthUser entity
        return User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getUserRole().toString())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
