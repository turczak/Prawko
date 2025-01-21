package pl.turlap.prawko.dto;

import lombok.Builder;
import lombok.Data;

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
    private Boolean enabled;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private List<RoleDto> roles;

}
