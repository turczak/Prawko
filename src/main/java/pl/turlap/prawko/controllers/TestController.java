package pl.turlap.prawko.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.User;
import pl.turlap.prawko.services.TestService;
import pl.turlap.prawko.services.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(path = "/tests")
@AllArgsConstructor
public class TestController {
    private final TestService testService;
    private final UserService userService;

    @GetMapping(path = "/new")
    public String newExam(HttpSession session,
                          Principal principal) {

        User user = userService.findByUserName(principal.getName());
        Language userLanguage = user.getLanguage();

        session.setAttribute("language", userLanguage);

        List<QuestionDto> questions = testService.generateTest(user.getId(), userLanguage);
        session.setAttribute("questions", questions);

        return "redirect:/exam?page=0";
    }

}
