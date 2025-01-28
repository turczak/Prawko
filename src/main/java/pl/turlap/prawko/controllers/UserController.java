package pl.turlap.prawko.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
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
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.services.CategoryService;
import pl.turlap.prawko.services.LanguageService;
import pl.turlap.prawko.services.UserService;

import java.security.Principal;
import java.util.List;


@Controller
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

        User existingUserByUsername = userService.findByUserName(registerDto.getUserName());

        if (existingUserByUsername != null && existingUserByUsername.getUserName() != null && !existingUserByUsername.getUserName().isEmpty()) {
            result.rejectValue("userName", null,
                    "There is already an account registered with the same username");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", registerDto);
            return "/register";
        }
        userService.saveUser(registerDto);
        return "redirect:/register?success";
    }

    @PostMapping("/setLanguage")
    public String setLanguage(@RequestParam(name = "languageCode") String languageCode,
                              Principal principal,
                              HttpSession session) {
        User user = userService.findByUserName(principal.getName());
        if (user != null) {
            Language language = languageService.findByCode(languageCode);
            if (language != null) {
                user.setLanguage(language);
                userService.save(user);
                session.setAttribute("currentLanguage", user.getLanguage());
            }
        }
        return "redirect:/index";
    }

    @PostMapping("/setCategory")
    public String setCategory(@RequestParam(name = "categoryName") String selectedCategory,
                              Principal principal,
                              HttpSession session) {
        User user = userService.findByUserName(principal.getName());
        if (user != null) {
            Category category = categoryService.findByName(selectedCategory);
            if (category != null) {
                user.setCategory(category);
                userService.save(user);
                session.setAttribute("currentCategory", user.getCategory());
            }
        }
        return "redirect:/index";
    }

}