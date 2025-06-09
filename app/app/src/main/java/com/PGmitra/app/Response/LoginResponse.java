package com.PGmitra.app.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String token;
    private HttpStatus status;
    private String message;

    public LoginResponse(String username, String message, HttpStatus status) {
        this.username = username;
        this.message = message;
        this.status = status;
        this.token = null;
    }
}
