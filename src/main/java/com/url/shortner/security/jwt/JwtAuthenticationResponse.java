package com.url.shortner.security.jwt;

import lombok.Data;


public class JwtAuthenticationResponse {

    private String Token;

    public JwtAuthenticationResponse(String token) {
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
