package com.example.auth.config;


import com.example.auth.Model.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public DefaultSecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(RestEndpoints.BASE_API + RestEndpoints.SIGN_UP).permitAll()
                .requestMatchers(RestEndpoints.BASE_API + RestEndpoints.SIGN_IN).permitAll()
                .requestMatchers(RestEndpoints.BASE_PROFILE_API+RestEndpoints.ADMIN_USER_PROFILE)
                    .hasRole(UserRole.ADMIN.name())
                .requestMatchers(RestEndpoints.BASE_PROFILE_API+RestEndpoints.BASIC_USER_PROFILE)
                    .hasRole(UserRole.BASIC.name())
                .requestMatchers(RestEndpoints.BASE_PROFILE_API+RestEndpoints.PREMIUM_USER_PROFILE)
                    .hasRole(UserRole.PREMIUM.name())
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityContextPersistenceFilter persistentSecurityContextFilter() {
        return new SecurityContextPersistenceFilter(securityContextRepository());
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        // Configure and return a suitable SecurityContextRepository implementation
        // For example, HttpSessionSecurityContextRepository for session-based storage
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
//        return authenticationManagerBuilder.build();
//    }
}
