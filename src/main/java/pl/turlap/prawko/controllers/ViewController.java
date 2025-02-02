package pl.turlap.prawko.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.dto.RegisterDto;
import pl.turlap.prawko.dto.UserPreferencesDto;
import pl.turlap.prawko.exceptions.CustomAlreadyExistsException;
import pl.turlap.prawko.exceptions.CustomNotFoundException;
import pl.turlap.prawko.models.Answer;
import pl.turlap.prawko.models.Test;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.services.AnswerService;
import pl.turlap.prawko.services.CategoryService;
import pl.turlap.prawko.services.LanguageService;
import pl.turlap.prawko.services.QuestionService;
import pl.turlap.prawko.services.TestService;
import pl.turlap.prawko.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class ViewController {

    private final CategoryService categoryService;
    private final LanguageService languageService;
    private final UserService userService;
    private final QuestionService questionService;
    private final TestService testService;
    private final AnswerService answerService;

    @GetMapping("/index")
    public String showHomePage(Principal principal,
                               Model model) {
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("preferences",
                new UserPreferencesDto(
                        user.getId(),
                        user.getCategory().getName(),
                        user.getLanguage().getCode()));
        model.addAttribute("user", user);
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
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Validated @ModelAttribute(name = "user") RegisterDto registerDto,
                               BindingResult result,
                               Model model) {
        try {
            userService.register(registerDto);
            return "redirect:/register?success";
        } catch (CustomAlreadyExistsException exception) {
            result.rejectValue(exception.getFieldName(), null, exception.getMessage());
            model.addAttribute("user", registerDto);
            return "register";
        }
    }

    @PostMapping("/editPreferences")
    public String editPreferences(@ModelAttribute(name = "preferences") UserPreferencesDto preferencesDto) {
        try {
            userService.updatePreferences(preferencesDto);
            return "redirect:/index?success";
        } catch (CustomNotFoundException exception) {
            return "index";
        }
    }

    @PostMapping(path = "/admin-panel/upload", consumes = {"multipart/form-data"})
    public String uploadQuestions(@RequestPart("file") MultipartFile file) {
        questionService.saveAll(file);
        return "redirect:/questions/upload?success";
    }

    @GetMapping(path = "/newExam")
    public String startNewExam(HttpSession session,
                               Principal principal) {
        User user = userService.findByUserName(principal.getName());
        Test test = testService.generateTest(user.getId());
        session.setAttribute("userId", user.getId());
        session.setAttribute("testId", test.getId());
        session.setAttribute("currentPage", 0);
        return "redirect:exam";
    }

    @GetMapping(path = "/exam")
    public String displayCurrentExam(Model model,
                                     HttpSession session) {
        Integer currentPage = (Integer) session.getAttribute("currentPage");
        List<QuestionDto> questions = testService.showQuestions((Long) session.getAttribute("testId"));
        model.addAttribute("question", questions.get(currentPage));
        return "exam";
    }

    @PostMapping(path = "/submitAnswer")
    public String submitUserAnswer(@RequestParam(name = "answer") Long answerId,
                                   HttpSession session) {
        Integer currentPage = (Integer) session.getAttribute("currentPage");
        Test activeTest = testService.findActiveUserTest((Long) session.getAttribute("userId"));
        Answer userAnswer = answerService.findAnswerById(answerId);
        testService.saveUserAnswer(activeTest, userAnswer);
        if (currentPage + 1 >= activeTest.getQuestions().size()) {
            activeTest.setIsActive(false);
            testService.saveTest(activeTest);
            return "redirect:/tests/results";
        }
        session.setAttribute("currentPage", currentPage + 1);
        return "redirect:/exam";
    }

}