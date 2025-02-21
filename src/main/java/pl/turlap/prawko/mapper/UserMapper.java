package pl.turlap.prawko.mapper;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.model.User;
import pl.turlap.prawko.service.CategoryService;
import pl.turlap.prawko.service.LanguageService;
import pl.turlap.prawko.service.RoleService;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final LanguageService languageService;
    private final CategoryService categoryService;
    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public User fromDto(RegisterDto registerDto) {
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(List.of(roleService.findByName("USER")));
        user.setEnabled(true);
        user.setLanguage(languageService.findByCode("PL"));
        user.setCategory(categoryService.findByName("B"));
        return user;
    }

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUserName(),
                user.getRoles().stream().map(roleMapper::toDto).toList());
    }

}
