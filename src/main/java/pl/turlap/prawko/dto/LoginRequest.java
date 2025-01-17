package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {

    private String username;
    private String email;
    private String password;

}
