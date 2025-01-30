package pl.turlap.prawko.services.implementation;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.exception.UserWithEmailExistsException;
import pl.turlap.prawko.exception.UserWithUserNameExistsException;
import pl.turlap.prawko.exception.UserNotFoundException;
import pl.turlap.prawko.mappers.UserMapper;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.RoleRepository;
import pl.turlap.prawko.repositories.UserRepository;
import pl.turlap.prawko.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
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
    public void register(RegisterDto registerDto) {
        if (checkIfExist(registerDto.getUserName())) {
            throw new UserWithUserNameExistsException("User with username: " + registerDto.getUserName() + " already exists.");
        } else if (checkIfExist(registerDto.getEmail())) {
            throw new UserWithEmailExistsException("User with email: " + registerDto.getEmail() + " already exists.");
        }
        User user = userMapper.fromRegisterToUser(registerDto);
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found."));
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException("User with username: " + username + " not found."));
    }

    @Override
    public void deleteUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getRoles().clear();
            userRepository.delete(user);
        }
    }

    @Override
    public boolean checkIfExist(String userNameOrEmail) {
        return userRepository.existsByUserName(userNameOrEmail) || userRepository.existsByEmail(userNameOrEmail);
    }

    @Override
    public boolean checkIfExist(String userName, String email) {
        return userRepository.existsByUserName(userName) || userRepository.existsByEmail(email);
    }

    @Override
    public void changeRole(Long userId, String newRole) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findByName(newRole);
        Optional<Role> optionalAdminRole = roleRepository.findByName("ADMIN");
        if (optionalUser.isPresent() && optionalRole.isPresent() && optionalAdminRole.isPresent()) {
            User user = optionalUser.get();
            Role adminRole = optionalAdminRole.get();
            switch (newRole) {
                case "ADMIN" -> {
                    if (!user.getRoles().contains(adminRole)) {
                        user.getRoles().add(adminRole);
                    }
                }
                case "USER" -> {
                    if (user.getRoles().contains(adminRole)) {
                        user.getRoles().remove(1);
                    }
                }
            }
            userRepository.save(user);
        }
    }

    @Override
    public List<Role> checkUserRoles(User user) {

        return List.of();
    }

    @Override
    public void editUser(UserDto userDto) {
        Optional<User> userToEdit = userRepository.findById(userDto.getId());
        if (userToEdit.isPresent()) {
            User user = userToEdit.get();
            restrictedUpdateOfUser(user, userDto);
            user.setUpdatedOn(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    @Override
    public void update(User user) {
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

