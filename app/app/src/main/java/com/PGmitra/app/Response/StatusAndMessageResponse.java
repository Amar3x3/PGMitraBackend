package com.PGmitra.app.Response;

import org.springframework.http.HttpStatus;

public record StatusAndMessageResponse(HttpStatus httpStatus, String message) {
}
