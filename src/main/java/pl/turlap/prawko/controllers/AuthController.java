package pl.turlap.prawko.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.turlap.prawko.dto.RegisterDto;

@Controller
public class AuthController {

    @GetMapping("/index")
    public String showHomePage() {
        return "index";
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
