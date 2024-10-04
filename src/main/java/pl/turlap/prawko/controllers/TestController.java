package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.turlap.prawko.dto.QuestionDto;
import pl.turlap.prawko.dto.UserDto;
import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.services.TestService;

import java.util.List;

@RestController
@RequestMapping(path = "/tests")
public class TestController {
    private TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/new")
    public List<QuestionDto> newTest(@RequestParam(name = "language")Language language){
        return testService.generateTest(language);
    }
}
