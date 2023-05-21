package com.example.auth.DTO;

import com.example.auth.Model.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserSignInRequest {
    @NotBlank(message = "username should not be blank")
    String username;

    @NotBlank(message = "password must not to blank")
    String password;

    @NotNull(message = "user role should be selected")
    UserRole userRole;
}
