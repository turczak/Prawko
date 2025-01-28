package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.services.CategoryService;
import pl.turlap.prawko.services.LanguageService;

@Controller
public class AuthController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LanguageService languageService;

    @GetMapping("/index")
    public String showHomePage(Model model) {
        model.addAttribute("languages", languageService.findAll());
        model.addAttribute("categories", categoryService.findAll());
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
