package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.RoleDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.services.UserService;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

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
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        return userService.deleteUserById(userId);
    }

    @RequestMapping(path = "/edit/{userId}", method = RequestMethod.PATCH, consumes = "application/json")
    public ResponseEntity<String> editUser(@PathVariable("userId") Long userId, @RequestBody UserDto userDto) {
        userDto.setId(userId);
        return userService.editUser(userDto);
    }

    @RequestMapping(path = "/roles/{userId}", method = RequestMethod.PATCH, consumes = "application/json")
    public ResponseEntity<String> changeRoles(@PathVariable("userId") Long userId, @RequestBody RoleDto roleDto) {
        return userService.changeRoles(userId, roleDto);
    }

    @PostMapping("/save")
    public String registration(@Validated @ModelAttribute("user") RegisterDto registerDto,
                               BindingResult result,
                               Model model) {
        User existingUserByEmail = userService.findByEmail(registerDto.getEmail());

        if (existingUserByEmail != null && existingUserByEmail.getEmail() != null && !existingUserByEmail.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        User existingUserByUsername = userService.findByUsername(registerDto.getUsername());

        if (existingUserByUsername != null && existingUserByUsername.getUsername() != null && !existingUserByUsername.getUsername().isEmpty()) {
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", registerDto);
            return "/register";
        }
        userService.saveUser(registerDto);
        return "redirect:/register?success";
    }

}
