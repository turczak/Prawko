package pl.turlap.prawko.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

}