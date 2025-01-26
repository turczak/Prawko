package pl.turlap.prawko.mappers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.LanguageRepository;

@Component
@AllArgsConstructor
public class UserMapper {

    private PasswordEncoder passwordEncoder;
    private RoleMapper roleMapper;
    private LanguageRepository languageRepository;

    public User fromRegisterToUser(RegisterDto registerDto) {
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(roleMapper.roleForNewUser());
        user.setEnabled(true);
        user.setLanguage(languageRepository.findByCode("PL"));
        return user;
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .userName(user.getUserName())
                .createdOn(user.getCreatedOn())
                .updatedOn(user.getUpdatedOn())
                .enabled(user.getEnabled())
                .roles(roleMapper.toRoleDto(user.getRoles()))
                .build();
    }

}
