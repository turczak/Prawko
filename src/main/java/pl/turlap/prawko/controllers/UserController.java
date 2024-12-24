package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.services.UserService;

import java.util.List;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "users/all2")
    public List<UserDto> findAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("users/all")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @DeleteMapping(path = "users/delete/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUserById(userId);
    }

    @PostMapping("/users/save")
    public String registration(@Validated @ModelAttribute("user") RegisterDto registerDto,
                               BindingResult result,
                               Model model){
        User existingUserByEmail = userService.findByEmail(registerDto.getEmail());

        if(existingUserByEmail != null && existingUserByEmail.getEmail() != null && !existingUserByEmail.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        User existingUserByUsername = userService.findByUsername(registerDto.getUsername());

        if(existingUserByUsername != null && existingUserByUsername.getUsername() != null && !existingUserByUsername.getUsername().isEmpty()){
            result.rejectValue("username", null,
                    "There is already an account registered with the same username");
        }

        if(result.hasErrors()){
            model.addAttribute("user", registerDto);
            return "/register";
        }
        userService.saveUser(registerDto);
        return "redirect:/register?success";
    }

}
