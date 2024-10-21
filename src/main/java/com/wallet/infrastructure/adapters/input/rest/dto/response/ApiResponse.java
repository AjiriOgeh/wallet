package com.wallet.infrastructure.adapters.input.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse {
    private Object response;
    private boolean isSuccessful;
}
