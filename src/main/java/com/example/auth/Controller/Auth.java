package com.example.auth.Controller;

import com.example.auth.DTO.UserRegistrationRequest;
import com.example.auth.DTO.UserSignInRequest;
import com.example.auth.Services.CustomUserDetailsService;
import com.example.auth.config.RestEndpoints;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoints.BASE_API)
public class Auth {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @GetMapping(RestEndpoints.SIGN_IN)
    public String signin(@RequestBody @Valid UserSignInRequest userSignInRequest) {
        return customUserDetailsService.signIn(userSignInRequest);
    }

    @PostMapping(RestEndpoints.SIGN_UP)
    public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
            customUserDetailsService.registerUser(userRegistrationRequest);
            return ResponseEntity.ok("user registration successful");
    }

    @GetMapping(RestEndpoints.PREMIUM_USER_PROFILE)
    public ResponseEntity<String> profileForPremium() {
        return ResponseEntity.ok("this page is for premium users");
    }

    @GetMapping(RestEndpoints.ADMIN_USER_PROFILE)
    public ResponseEntity<String> profileForAdmin() {
        return ResponseEntity.ok("this page is for admin users");
    }

    @GetMapping(RestEndpoints.BASIC_USER_PROFILE)
    public ResponseEntity<String> profileForBasic() {
        return ResponseEntity.ok("this page is for basic users");
    }
}
