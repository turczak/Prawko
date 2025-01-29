package pl.turlap.prawko.services.implementation;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.exception.UserNotFoundException;
import pl.turlap.prawko.mappers.UserMapper;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.RoleRepository;
import pl.turlap.prawko.repositories.UserRepository;
import pl.turlap.prawko.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public void saveUser(RegisterDto registerDto) {
        User userToSave = userMapper.fromRegisterToUser(registerDto);
        userRepository.save(userToSave);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public ResponseEntity<String> deleteUserById(Long id) {
        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isPresent()) {
            User user = userToDelete.get();
            user.getRoles().clear();
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @Override
    public ResponseEntity<String> changeRoles(Long id, RoleDto roleDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            switch (roleDto.getName()) {
                case "ADMIN" -> {
                    if (!user.getRoles().contains(roleRepository.findByName("ADMIN"))) {
                        user.getRoles().add(roleRepository.findByName("ADMIN"));
                    }
                }
                case "USER" -> {
                    if (user.getRoles().contains(roleRepository.findByName("ADMIN"))) {
                        user.getRoles().remove(1);
                    }
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found.");
                }
            }
            userRepository.save(user);
            return ResponseEntity.ok("User roles changed.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @Override
    public ResponseEntity<String> editUser(UserDto userDto) {
        Optional<User> userToEdit = userRepository.findById(userDto.getId());
        if (userToEdit.isPresent()) {
            User user = userToEdit.get();
            restrictedUpdateOfUser(user, userDto);
            user.setUpdatedOn(LocalDateTime.now());
            userRepository.save(user);
            return ResponseEntity.ok("User updated successfully.");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
    }

    private void restrictedUpdateOfUser(User user, UserDto userDto) {
        if (userDto.getFirstName() != null && !userDto.getFirstName().isBlank()) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null && !userDto.getLastName().isBlank()) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getUserName() != null && !userDto.getUserName().isBlank() && !userRepository.existsByUserName(userDto.getUserName())) {
            user.setUserName(userDto.getUserName());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank() && !userRepository.existsByEmail(userDto.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
    }

}

