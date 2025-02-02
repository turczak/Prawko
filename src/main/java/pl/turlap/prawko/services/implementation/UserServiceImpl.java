package pl.turlap.prawko.services.implementation;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.dto.UserPreferencesDto;
import pl.turlap.prawko.exceptions.CustomAlreadyExistsException;
import pl.turlap.prawko.exceptions.CustomNotFoundException;
import pl.turlap.prawko.mappers.UserMapper;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Role;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.repositories.UserRepository;
import pl.turlap.prawko.services.CategoryService;
import pl.turlap.prawko.services.LanguageService;
import pl.turlap.prawko.services.RoleService;
import pl.turlap.prawko.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final LanguageService languageService;
    private final CategoryService categoryService;

    @Override
    public void register(RegisterDto registerDto) {
        if (checkIfExist(registerDto.getUserName())) {
            throw new CustomAlreadyExistsException("userName", "User with username '" + registerDto.getUserName() + "' already exists.");
        } else if (checkIfExist(registerDto.getEmail())) {
            throw new CustomAlreadyExistsException("email", "User with email '" + registerDto.getEmail() + "' already exists.");
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
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomNotFoundException(email, "User with email '" + email + "' not found."));
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new CustomNotFoundException("userName", "User with username '" + userName + "' not found."));
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("userId", "User with id '" + userId + "' not found."));
        user.getRoles().clear();
        userRepository.delete(user);
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
    public void changeUserRoles(Long userId, String newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("userId", "User with id '" + userId + "' not found."));
        roleService.findByName(newRole);
        Role adminRole = roleService.findByName("ADMIN");
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
            default -> throw new CustomNotFoundException(newRole, "Role '" + newRole + "' not found.");
        }
        userRepository.save(user);
    }

    @Override
    public List<Role> checkUserRoles(User user) {

        return List.of();
    }

    @Override
    public void editUser(UserDto userDto) {
        User user  = findById(userDto.getId());
        if(userDto.getFirstName() != null && !userDto.getFirstName().isBlank()){
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
        user.setUpdatedOn(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("userId", "User with id " + userId + " not found."));
    }

    @Override
    public void updatePreferences(UserPreferencesDto userPreferencesDto) {
        User user = findById(userPreferencesDto.getUserId());
        if (userPreferencesDto.getLanguageCode() != null && !userPreferencesDto.getLanguageCode().isBlank()) {
            Language language = languageService.findByCode(userPreferencesDto.getLanguageCode());
            user.setLanguage(language);
        }
        if (userPreferencesDto.getCategoryName() != null && !userPreferencesDto.getCategoryName().isBlank()) {
            Category category = categoryService.findByName(userPreferencesDto.getCategoryName());
            user.setCategory(category);
        }
        userRepository.save(user);
    }
}

