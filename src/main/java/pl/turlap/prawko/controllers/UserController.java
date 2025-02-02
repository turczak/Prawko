package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.dto.UserPreferencesDto;
import pl.turlap.prawko.services.UserService;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserDto> showAllUsers() {
        return userService.findAllUsers();
    }

    @PatchMapping(path = "/edit")
    public ResponseEntity<Map<String, String>> editUser(@RequestBody UserDto userDto) {
        userService.editUser(userDto);
        return ResponseEntity.ok(Map.of("message", "User edited."));
    }

    @DeleteMapping(path = "/delete{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestParam(name = "userId") Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User with id '" + userId + "' successfully deleted."));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User has been successfully registered."));
    }

    @PatchMapping(path = "/{userId}/roles")
    public ResponseEntity<Map<String, String>> changeRoles(@PathVariable(name = "userId") Long userId,
                                                           @RequestParam(name = "roleName") String roleName) {
        userService.changeUserRoles(userId, roleName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message", "Roles updated successfully."));
    }

    @PatchMapping(value = "/editPreferences", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> editPreferences(@RequestBody UserPreferencesDto userPreferencesDto) {
        userService.updatePreferences(userPreferencesDto);
        return ResponseEntity.ok(Map.of("message", "Preferences updated successfully."));
    }

}