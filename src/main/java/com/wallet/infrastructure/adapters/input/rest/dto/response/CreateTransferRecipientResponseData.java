package com.wallet.infrastructure.adapters.input.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTransferRecipientResponseData {
    private boolean active;
    private String currency;
    private Long id;
    @JsonProperty("recipient_code")
    private String recipientCode;
    private String type;
}
