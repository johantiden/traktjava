package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDto {
    public final String token;

    @JsonCreator
    public TokenDto(
            @JsonProperty("access_token") String token,
            @JsonProperty("token_type") String tokenType,
            @JsonProperty("expires_in") int expiresIn,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("scope") String scopre
    ) {

        this.token = token;
    }
}
