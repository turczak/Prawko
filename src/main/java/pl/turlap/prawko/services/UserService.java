package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService{
    void saveUser(RegisterDto registerDto);
    User findByEmail(String email);
    User findByUsername(String username);
    List<UserDto> findAllUsers();
    void deleteUserById(Long id);
}
