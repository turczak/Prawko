package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.services.CategoryService;
import pl.turlap.prawko.services.LanguageService;
import pl.turlap.prawko.services.UserService;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class AuthController {

    private CategoryService categoryService;
    private LanguageService languageService;
    private UserService userService;

    @GetMapping("/index")
    public String showHomePage(Model model, Principal principal) {
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("categories", categoryService.findAll());

        User user = userService.findByUserName(principal.getName());
        model.addAttribute("currentLanguage", user.getLanguage());
        model.addAttribute("currentCategory", user.getCategory());

        return "index";
    }

    @GetMapping("/admin-panel")
    public String showAdminPanel() {
        return "admin-panel";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute("user", registerDto);
        return "register";
    }

    @GetMapping("/questions/upload")
    public String showUploadForm() {
        return "upload";
    }

}
