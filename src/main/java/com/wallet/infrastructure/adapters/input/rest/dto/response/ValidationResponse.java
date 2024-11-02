package com.wallet.infrastructure.adapters.input.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationResponse {
    private boolean status;
    private String detail;
}
