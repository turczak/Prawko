package pl.turlap.prawko.services.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.mappers.RoleMapper;
import pl.turlap.prawko.mappers.UserMapper;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.RoleRepository;
import pl.turlap.prawko.repositories.UserRepository;
import pl.turlap.prawko.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = new UserMapper(passwordEncoder, new RoleMapper(this.roleRepository));
    }

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
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<String> deleteUserById(Long id) {
        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isPresent()) {
            User user = userToDelete.get();
            user.getRoles().clear();
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted successfully.");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @Override
    public ResponseEntity<String> promoteUser(Long id) {
        Optional<User> userToPromote = userRepository.findById(id);
        if (userToPromote.isPresent()) {
            User user = userToPromote.get();
            user.getRoles().add(roleRepository.findByName("ADMIN"));
            userRepository.save(user);
            return ResponseEntity.ok("User promoted.");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
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

    private void restrictedUpdateOfUser(User user, UserDto userDto) {
        if (userDto.getFirstname() != null && !userDto.getFirstname().isBlank()) {
            user.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null && !userDto.getLastname().isBlank()) {
            user.setLastname(userDto.getLastname());
        }
        if (userDto.getUsername() != null && !userDto.getUsername().isBlank() && !userRepository.existsByUsername(userDto.getUsername())) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank() && !userRepository.existsByEmail(userDto.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
    }
}

