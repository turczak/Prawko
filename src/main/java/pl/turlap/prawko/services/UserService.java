package pl.turlap.prawko.services;

import org.springframework.http.ResponseEntity;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.User;

import java.util.List;

public interface UserService {

    ResponseEntity<String> changeRoles(Long id, RoleDto roleDto);

    void saveUser(RegisterDto registerDto);

    User findByEmail(String email);

    User findByUserName(String username);

    List<UserDto> findAllUsers();

    ResponseEntity<String> deleteUserById(Long id);

    ResponseEntity<String> editUser(UserDto userDto);

}
