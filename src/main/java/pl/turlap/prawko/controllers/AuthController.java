package pl.turlap.prawko.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.dto.RegisterDto;

import java.util.List;

@Controller
public class AuthController {

    @GetMapping("/index")
    public String showHomePage() {
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

    @GetMapping("/exam{page}")
    public String showQuestionFromExam(@RequestParam(name = "page", defaultValue = "0") int page,
                                       HttpSession session,
                                       Model model) {


        List<QuestionDto> questions = (List<QuestionDto>) session.getAttribute("questions");

        QuestionDto question = questions.get(page);

        model.addAttribute("question", question);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", questions.size());

        return "exam";
    }

}
