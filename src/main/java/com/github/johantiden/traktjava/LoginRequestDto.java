package com.github.johantiden.traktjava;

public class LoginRequestDto {
    private final String code;
    private final String client_id;
    private final String client_secret;
    private final String redirect_uri;
    private final String grant_type;


    public LoginRequestDto(String code, String client_id, String client_secret, String redirect_uri, String grant_type) {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
        this.grant_type = grant_type;
    }

    public String getCode() {
        return code;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public String getGrant_type() {
        return grant_type;
    }
}
