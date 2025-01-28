package pl.turlap.prawko.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.models.*;
import pl.turlap.prawko.services.AnswerService;
import pl.turlap.prawko.services.CategoryService;
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
    private final CategoryService categoryService;
    private final AnswerService answerService;

    @GetMapping(path = "/newExam")
    public String newExam(@RequestParam(name = "category") String selectedCategory,
                          HttpSession session,
                          Principal principal) {

        User user = userService.findByUserName(principal.getName());
        Language userLanguage = user.getLanguage();
        Category category = categoryService.findByName(selectedCategory);

        session.setAttribute("language", userLanguage);

        List<QuestionDto> questions = testService.generateTest(user.getId(), userLanguage, category);
        session.setAttribute("questions", questions);

        session.setAttribute("currentPage", 0);

        return "redirect:/tests/exam";
    }

    @GetMapping(path = "/exam")
    public String displayExam(Model model,
                              HttpSession session) {
        Integer currentPage = (Integer) session.getAttribute("currentPage");
        List<QuestionDto> questions = (List<QuestionDto>) session.getAttribute("questions");

        QuestionDto currentQuestion = questions.get(currentPage);

        model.addAttribute("question", currentQuestion);
        model.addAttribute("totalPages", questions.size());

        return "exam";
    }

    @PostMapping(path = "/submitAnswer")
    public String submitUserAnswer(@RequestParam(name = "answer") Long answerId,
                                   HttpSession session,
                                   Principal principal) {

        Integer currentPage = (Integer) session.getAttribute("currentPage");
        List<QuestionDto> questions = (List<QuestionDto>) session.getAttribute("questions");

        User user = userService.findByUserName(principal.getName());
        Test activeTest = testService.findActiveUserTest(user.getId());
        Answer userAnswer = answerService.findAnswerById(answerId);

        testService.saveUserAnswer(activeTest, userAnswer);

        session.setAttribute("currentPage", currentPage + 1);

        if (currentPage + 1 >= questions.size()) {
            activeTest.setIsActive(false);
            testService.saveTest(activeTest);
            return "redirect:/tests/results";
        }

        return "redirect:/tests/exam";
    }

}
