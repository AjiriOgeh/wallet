package com.wallet.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("refresh_expires_in")
    private int refreshExpiresIn;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("not-before-policy")
    private int notBeforePolicy;
    @JsonProperty("session_state")
    private String sessionState;
    @JsonProperty("scope")
    private String scope;
}