package com.wallet.domain.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    private String keycloakId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
