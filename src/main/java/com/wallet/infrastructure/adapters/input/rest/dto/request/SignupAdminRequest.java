package com.wallet.infrastructure.adapters.input.rest.dto.request;

import com.wallet.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static com.wallet.domain.model.Role.ADMIN;

@Setter
@Getter
public class SignupAdminRequest {
    @NotNull(message = "Firstname cannot be null")
    @Size(min = 2, message = "Firstname must be at least 2 characters")
    private String firstname;
    @NotNull(message = "Lastname cannot be null")
    @Size(min = 2, message = "Lastname must be at least 2 characters")
    private String lastname;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be valid")
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;
    private Role role = ADMIN;
}
