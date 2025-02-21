package pl.turlap.prawko.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.QuestionDto;

import pl.turlap.prawko.service.QuestionService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(path = "/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<QuestionDto> findAllQuestions(@RequestParam(name = "language", required = false, defaultValue = "PL") String language) {
        return questionService.findAllQuestionsByLanguage(language);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Optional<QuestionDto> findQuestionById(@PathVariable(name = "id") Long id,
                                                  @RequestParam(name = "language", required = false, defaultValue = "PL") String language) {
        return questionService.findById(id, language);
    }

    @GetMapping(path = "/all/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<QuestionDto> findAllByType(@PathVariable(name = "type") String type,
                                           @RequestParam(name = "language", required = false, defaultValue = "PL") String language) {
        return questionService.findAllByType(type, language);
    }

    @PostMapping(path = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> saveQuestionsFromCSV(@RequestPart("file") MultipartFile file) {
        questionService.saveAll(file);
        return ResponseEntity.ok(Map.of("message", "Questions from csv file successfully added."));
    }

}
