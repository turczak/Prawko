package pl.turlap.prawko.dto;

import lombok.*;

@Data
public class RegisterDto {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}