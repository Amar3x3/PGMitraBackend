package com.PGmitra.app.Response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class LoginResponse {
    private String username;
    private String accessToken;
    private String refreshToken;
    private HttpStatus status;
    private String message;
    private Long userId;

    public LoginResponse(String username, String accessToken, String refreshToken, HttpStatus status, Long userId) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.status = status;
        this.message = "Login successful";
        this.userId = userId;
    }

    public LoginResponse(String username, HttpStatus status, String message) {
        this.username = username;
        this.accessToken = null;
        this.refreshToken = null;
        this.status = status;
        this.message = message;
    }
}
