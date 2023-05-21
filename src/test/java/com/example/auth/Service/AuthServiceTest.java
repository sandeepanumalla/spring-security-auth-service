package com.example.auth.Service;

import com.example.auth.DTO.UserRegistrationRequest;
import com.example.auth.Services.CustomUserDetailsService;
import com.example.auth.Model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Test
    void saveUserTest() {
        UserRegistrationRequest newUser = UserRegistrationRequest.builder()
                .username("sandeep2")
                .password("password")
                .userRole(UserRole.PREMIUM)
                .build();

        customUserDetailsService.registerUser(newUser);
    }
}
