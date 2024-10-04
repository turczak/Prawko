package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.dto.QuestionDto;

import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.services.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/questions")
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/all{language}")
    public List<QuestionDto> findAllQuestions(@RequestParam(name = "language") Language language) {
        return questionService.findAllQuestionsByLanguage(language);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/{id}")
    public Optional<Question> findQuestionById(@PathVariable("id") Long id) {
        return questionService.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/all/{language}{type}{value}")
    public List<QuestionDto> findALlQuestionsByTypeAndValue(@RequestParam(name = "type") String type,
                                                            @RequestParam(name = "value") int value,
                                                            @RequestParam(name = "language") Language language) {
        return questionService.findALlByTypeAndValue(type, value, language);
    }

}
