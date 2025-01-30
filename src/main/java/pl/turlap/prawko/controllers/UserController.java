package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.dto.UserPreferencesDto;
import pl.turlap.prawko.exception.*;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.services.CategoryService;
import pl.turlap.prawko.services.LanguageService;
import pl.turlap.prawko.services.UserService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private LanguageService languageService;
    private CategoryService categoryService;

    // html view, table with users
    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // JSON
    @RequestMapping(path = "/all-2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserDto> showAllUsers() {
        return userService.findAllUsers();
    }

    @RequestMapping(path = "/delete/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
    }

//    @RequestMapping(path = "/edit/{userId}", method = RequestMethod.PATCH, consumes = "application/json")
//    public ResponseEntity<String> editUser(@PathVariable("userId") Long userId, @RequestBody UserDto userDto) {
//        userDto.setId(userId);
//        return userService.editUser(userDto);
//    }

    @PatchMapping(path = "/roles/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void changeRoles(@PathVariable("userId") Long userId, @RequestParam(name = "role") String role) {
        userService.changeRole(userId, role);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User has been successfully registered."));
    }

    @ExceptionHandler({UserWithUserNameExistsException.class, UserWithEmailExistsException.class})
    public ResponseEntity<Map<String, String>> handleException(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("field", exception.getFieldName(), "message", exception.getMessage()));
    }

    @PatchMapping(value = "/editPreferences", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editPreferences(@RequestBody UserPreferencesDto userPreferencesDto) {
        Long userId = userPreferencesDto.getUserId();
        String languageCode = userPreferencesDto.getLanguageCode();
        String categoryName = userPreferencesDto.getCategoryName();
        boolean categoryUpdated = false;
        boolean languageUpdated = false;
        try {
            User user = userService.findById(userId);
            if (!languageCode.isBlank()) {
                Language language = languageService.findByCode(languageCode);
                user.setLanguage(language);
                languageUpdated = true;
            }
            if (!categoryName.isBlank()) {
                Category category = categoryService.findByName(categoryName);
                user.setCategory(category);
                categoryUpdated = true;
            }
            if (!languageUpdated && !categoryUpdated) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("No preferences provided.");
            }
            userService.update(user);
            return ResponseEntity.ok("Preferences updated successfully.");
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (LanguageNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language code.");
        } catch (CategoryNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category name.");
        }
    }

}