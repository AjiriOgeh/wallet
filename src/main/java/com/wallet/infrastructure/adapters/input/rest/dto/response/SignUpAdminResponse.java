package com.wallet.infrastructure.adapters.input.rest.dto.response;

import com.wallet.domain.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpAdminResponse {
    private String keycloakId;
    private String email;
    private Role role;
}
