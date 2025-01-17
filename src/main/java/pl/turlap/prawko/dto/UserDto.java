package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Data;
import pl.turlap.prawko.models.Role;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<Role> roles;

}
