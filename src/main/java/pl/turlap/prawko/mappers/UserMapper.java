package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.User;

@Component
@AllArgsConstructor
public class UserMapper {

    private PasswordEncoder passwordEncoder;
    private RoleMapper roleMapper;

    public User fromRegisterToUser(RegisterDto registerDto) {
        User user = new User();
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(roleMapper.roleForNewUser());
        user.setEnabled(true);
        return user;
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .username(user.getUsername())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .enabled(user.getEnabled())
                .roles(roleMapper.toRoleDto(user.getRoles()))
                .build();
    }

}
