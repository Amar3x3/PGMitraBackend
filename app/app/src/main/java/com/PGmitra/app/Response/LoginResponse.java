package com.PGmitra.app.Response;

import org.springframework.http.HttpStatus;

public record LoginResponse (String username, String message, HttpStatus httpStatus){

}
