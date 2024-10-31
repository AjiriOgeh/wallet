package com.wallet.infrastructure.adapters.input.rest.dto.request;

import com.wallet.domain.model.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
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
    @NotNull(message = "Phone number cannot be null")
    @Size(min = 11, max = 11, message = "Phone number must be 11 digits")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only numeric characters")
    private String phoneNumber;
    @NotNull(message = "Bank verification number cannot be null")
    @Size(min = 11, max = 11, message = "Bank verification number must be 11 digits")
    @Pattern(regexp = "\\d+", message = "Bank verification number must contain only numeric characters")
    private String bankVerificationNumber;
}
