package com.url.shortner.dtos;

import jakarta.validation.constraints.NotBlank;

public class UrlShortenRequest {

    @NotBlank(message = "originalUrl is required")
    private String originalUrl;

    // getters and setters
    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }
}