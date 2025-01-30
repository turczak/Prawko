package pl.turlap.prawko.services;

import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.models.User;

import java.util.List;

public interface UserService {

    void changeRole(Long userId, String newRole);

    List<Role> checkUserRoles(User user);

    void saveUser(RegisterDto registerDto);

    User findByEmail(String email);

    User findByUserName(String userName);

    List<UserDto> findAllUsers();

    void deleteUserById(Long id);

    void editUser(UserDto userDto);

    void update(User user);

    User findById(Long userId);

    boolean checkIfExist(String UserNameOrEmail);

    boolean checkIfExist(String userName, String email);

    void register(RegisterDto registerDto);

}
