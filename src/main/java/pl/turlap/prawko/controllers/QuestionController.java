package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.QuestionDto;

import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.QuestionType;
import pl.turlap.prawko.services.QuestionService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/all/{language}")
    @ResponseBody
    public List<QuestionDto> findAllQuestions(@PathVariable String language) {
        return questionService.findAllQuestionsByLanguage(language);
    }

    @GetMapping("/find/{id}/{language}")
    @ResponseBody
    public Optional<QuestionDto> findQuestionById(@PathVariable(name = "id") Long id,
                                                  @PathVariable(name = "language") String language) {
        return questionService.findById(id, language);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/all/{language}/{type}/{value}")
    public List<QuestionDto> findAllByTypeAndValue(@RequestParam(name = "type") QuestionType type,
                                                   @RequestParam(name = "value") int value,
                                                   @RequestParam(name = "language") Language language) {
        return questionService.findAllByTypeAndValue(type, value, language);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/all/{language}/{type}")
    public List<QuestionDto> findAllQuestionsByType(@RequestParam(name = "type") QuestionType type,
                                                    @RequestParam(name = "language") Language language) {
        return questionService.findAllByType(type, language);
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public String uploadQuestions(@RequestPart("file") MultipartFile file) {
        questionService.saveAll(file);
        return "redirect:/questions/upload?success";
    }

}
