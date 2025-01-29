package pl.turlap.prawko.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserPreferencesDto;
import pl.turlap.prawko.models.Category;
import pl.turlap.prawko.models.Language;
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
    public String showHomePage(Principal principal,
                               Model model) {
        UserPreferencesDto preferences = new UserPreferencesDto();
        User user = userService.findByUserName(principal.getName());
        preferences.setUserId(user.getId());
        model.addAttribute("user", user);

        Language currentLanguage = user.getLanguage();
        model.addAttribute("currentLanguage", currentLanguage);
        preferences.setLanguageCode(currentLanguage.getCode());

        Category currentCategory = user.getCategory();
        model.addAttribute("currentCategory", currentCategory);
        preferences.setCategoryName(currentCategory.getName());

        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("preferences", preferences);
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
