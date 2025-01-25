package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.services.TestService;
import pl.turlap.prawko.services.UserService;

import java.util.List;

@Controller
@RequestMapping(path = "/tests")
@AllArgsConstructor
public class TestController {
    private final TestService testService;
    private final UserService userService;

    @GetMapping(path = "/new/{userId}{language}")
    public List<QuestionDto> newExam(@PathVariable(name = "userId") Long userId,
                                     @RequestParam(name = "language") Language language) {
        return testService.generateTest(userId, language);
    }

}
