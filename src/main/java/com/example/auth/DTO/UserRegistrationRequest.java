package com.example.auth.DTO;

import com.example.auth.Model.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.auth.Model.AuthUser}
 */


@Data
@AllArgsConstructor
@Builder
public class UserRegistrationRequest {
    @NotBlank(message = "username should not be blank")
    @Size(min = 6, max = 10, message = "username should be between 6 to 10 characters")
    String username;

    @NotBlank(message = "password must not to blank")
    @Size(min = 8, max = 32, message = "password should be between 8 to 32 characters")
    String password;

    @NotNull(message = "user role should be selected")
    UserRole userRole;
}