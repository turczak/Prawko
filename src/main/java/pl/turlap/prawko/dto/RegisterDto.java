package pl.turlap.prawko.dto;

import lombok.*;

@Data
public class RegisterDto {

    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;

}