package pl.turlap.prawko.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.turlap.prawko.dto.QuestionDto;

import pl.turlap.prawko.models.Language;
import pl.turlap.prawko.models.Question;
import pl.turlap.prawko.services.QuestionService;
import pl.turlap.prawko.utils.CsvUtility;

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

    @PostMapping(path = "/upload/csv")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file){
        String message = "";
        if(CsvUtility.hasCsvFormat(file)){
            try {
                questionService.saveAllFromFile(file);
                message = "The file is uploaded successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Upload failed: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload an *.csv file.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
