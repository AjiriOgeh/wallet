package com.wallet.domain.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
